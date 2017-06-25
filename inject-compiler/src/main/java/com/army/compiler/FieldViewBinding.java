package com.army.compiler;

import javax.lang.model.type.TypeMirror;

/**
 * Created by Army on 2017/06/25.
 */

/**
 *     @BindView(R.id.textview)
 *      TextView textView;
 */
public class FieldViewBinding {
    private String name;        //textView
    private TypeMirror type;    //TextView
    private int resId;          //R.id.textview

    public FieldViewBinding(String name, TypeMirror type, int resId) {
        this.name = name;
        this.type = type;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public TypeMirror getType() {
        return type;
    }

    public int getResId() {
        return resId;
    }

    @Override
    public String toString() {
        return "FieldViewBinding{" +
                "name='" + name + '\'' +
                ", TypeMirror=" + type.getKind() +
                ", resId=" + resId +
                '}';
    }
}
