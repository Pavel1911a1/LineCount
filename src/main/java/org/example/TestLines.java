package org.example;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;

import java.io.IOException;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class TestLines extends ASTVisitor {

    public static void main(String[] args) throws IOException, NoSuchFileException {
        char[] source = readToCharArray(getTestingPathFile());
        ASTParser parser = ASTParser.newParser(AST.JLS21);
        parser.setSource(source);

        Map options = JavaCore.getOptions();
        JavaCore.setComplianceOptions(JavaCore.VERSION_21, options);
        parser.setCompilerOptions(options);
        CompilationUnit result = (CompilationUnit) parser.createAST(null);

        List<TypeDeclaration> listOfTypes = result.types();
        for (TypeDeclaration td : listOfTypes) {
            MethodDeclaration[] methods = td.getMethods();
            for (MethodDeclaration m : methods) {
                Block body = m.getBody();
                List statments = body.statements();
                for (var currentStatement : statments) {
                    if (currentStatement instanceof ExpressionStatement) {
                        System.out.println("Current statement  = " + currentStatement.toString());
                        ExpressionStatement es = (ExpressionStatement) currentStatement;
                        int lineNumber = result.getLineNumber(es.getStartPosition());
                        System.out.println("Current statement line number = " + lineNumber);
                    }
                }
            }
        }
        System.out.println("Stop for debugger");
    }

    private static String getTestingPathFile() {
        InputStream inputStream = TestLines.class.getClassLoader().getResourceAsStream("TestWrongLineNumber.java");
        if (inputStream == null) {
            return "File not found!";
        }
        String resourcePath = null;
        try {
            resourcePath = Paths.get(TestLines.class.getClassLoader().getResource("TestWrongLineNumber.java").toURI()).toString();
            System.out.println("Path of testing file: " + resourcePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resourcePath;
    }

    private static char[] readToCharArray(String resourcePath) throws IOException ,NoSuchFileException {
        return Files.readString(Path.of(resourcePath)).toCharArray();
    }


}