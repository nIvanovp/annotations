package com.annotation.proc;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Set;

/**
 * Created by nikolai on 02.03.2015.
 */
@SupportedAnnotationTypes({"*"})
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class CustomProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for(Element element : roundEnv.getElementsAnnotatedWith(CustomAnnotation.class)){
            CustomAnnotation customAnnotation = element.getAnnotation(CustomAnnotation.class);
            TypeElement clazz = (TypeElement) element.getEnclosingElement();
            TypeMirror type = element.asType();

            String name = element.getSimpleName().toString();
            char[] c = name.toCharArray();
            c[0] = Character.toUpperCase(c[0]);
            name = new String(name);

            try {
                JavaFileObject javaFileObject = processingEnv.getFiler().createSourceFile(clazz.getSimpleName() + "Autogenerate");
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Creating"+javaFileObject.toUri());

                Writer writer = javaFileObject.openWriter();
                String classPackage = clazz.getQualifiedName().toString();

                PrintWriter printWriter = new PrintWriter(writer);
                printWriter.println("package " + classPackage.substring(0, classPackage.lastIndexOf('.')) + ";");
                printWriter.println("\npublic class " + clazz.getSimpleName() + "Autogenerate {");
                printWriter.println("\n    public " + customAnnotation.className() + " result = \"" + customAnnotation.value() + "\";");
                printWriter.println("    public int type = " + customAnnotation.type() + ";");
                printWriter.println("\n    protected " + clazz.getSimpleName() + "Autogenerate() {}");
                printWriter.println("\n    /** Handle something. */");
                printWriter.println("    protected final void handle" + name + "(" + customAnnotation.className() + " value" + ") {");
                printWriter.println("\n//" + element + ", type " + type);
                printWriter.println("//" + customAnnotation);
                printWriter.println("\n        System.out.println(value);");
                printWriter.println("    }");
                printWriter.println("}");
                printWriter.flush();

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.toString());
            }

        }
        return true;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }
}