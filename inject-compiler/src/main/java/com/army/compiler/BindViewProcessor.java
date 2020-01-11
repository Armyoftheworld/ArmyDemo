package com.army.compiler;

import com.army.annotation.BindView;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

@AutoService(Processor.class)
public class BindViewProcessor extends AbstractProcessor {

    /**
     * 处理节点的工具类
     */
    private Elements elementUtils;

    private Types typeUtils;

    /**
     * 生成java文件
     */
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementUtils = processingEnvironment.getElementUtils();
        typeUtils = processingEnvironment.getTypeUtils();
        filer = processingEnvironment.getFiler();
    }

    /**
     * 处理那些注解
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(BindView.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * package com.example;    // PackageElement
     * <p>
     * public class Foo {        // TypeElement
     * <p>
     * private int a;      // VariableElement
     * private Foo other;  // VariableElement
     * <p>
     * public Foo () {}    // ExecuteableElement
     * <p>
     * public void setA (  // ExecuteableElement
     * int newA   // TypeElement
     * ) {}
     * }
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //TypeElement相当于每个拥有BindView注解的类
        Map<TypeElement, List<FieldViewBinding>> targetMap = new HashMap<>();
        for (Element element : roundEnvironment.getElementsAnnotatedWith(BindView.class)) {
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            List<FieldViewBinding> list = targetMap.get(enclosingElement);
            if (list == null) {
                list = new ArrayList<>();
                targetMap.put(enclosingElement, list);
            }
            int id = element.getAnnotation(BindView.class).value();
            String fieldName = element.getSimpleName().toString();
            TypeMirror typeMirror = element.asType();
            FieldViewBinding fieldViewBinding = new FieldViewBinding(fieldName, typeMirror, id);
            LogUtils.print(fieldViewBinding.toString());
            list.add(fieldViewBinding);
        }
        for (Map.Entry<TypeElement, List<FieldViewBinding>> typeElementListEntry : targetMap.entrySet()) {
            List<FieldViewBinding> value = typeElementListEntry.getValue();
            if (value == null || value.isEmpty()) {
                continue;
            }
            TypeElement enClosingElement = typeElementListEntry.getKey();
            String packageName = getPackageName(enClosingElement);
            String compile = getClassName(enClosingElement, packageName);
            ClassName className = ClassName.bestGuess(compile);
            ClassName viewBinder = ClassName.get("com.army.inject", "ViewBinder");
            TypeSpec.Builder result = TypeSpec.classBuilder(compile + "$$ViewBinder")
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(ParameterizedTypeName.get(viewBinder, className))//viewBinder：接口名，className：接口需要的泛型
                    ;

            String methodParameterName = "target";
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("bind")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.VOID)
                    .addAnnotation(Override.class)
                    .addParameter(className, methodParameterName, Modifier.FINAL);

            for (FieldViewBinding fieldViewBinding : value) {
                String packageNameString = fieldViewBinding.getType().toString();
                ClassName viewClass = ClassName.bestGuess(packageNameString);
                methodBuilder.addStatement(methodParameterName + ".$L = ($T)" + methodParameterName + ".findViewById($L)",
                        fieldViewBinding.getName(), viewClass, fieldViewBinding.getResId());
            }
            result.addMethod(methodBuilder.build());
            try {
                JavaFile.builder(packageName, result.build())
                        .addFileComment("auto create, do not modify")
                        .build().writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    private String getClassName(TypeElement enClosingElement, String packageName) {
        int length = packageName.length() + 1;
        return enClosingElement.getQualifiedName().toString().substring(length).replaceAll("\\.", "\\$");
    }

    private String getPackageName(TypeElement enclosingElement) {
        return elementUtils.getPackageOf(enclosingElement).getQualifiedName().toString();
    }
}
