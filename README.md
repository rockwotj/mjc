# Mini Java Compiler

[![Build Status](https://travis-ci.org/rockwotj/mjc.svg?branch=master)](https://travis-ci.org/rockwotj/mjc)

A compiler for a small subset of the Java Programming Language

## Project Specification

The syntax for the language, and the specifications can be found in
the docs directory.

## Development

### Running the Project

This project uses gradle to run, build and manage dependencies. 
You can invoke gradle using `./gradlew` on unix systems or via 
`gradlew.bat` on windows system if you don't have gradle installed
yourself. These 'wrapper' scripts can be used in place of the `gradle`
command below.

The project can be built, along with the grammer being generated using 
antlr by running `gradle build`. You can run the compiler via 
`gradle run` and finally you can run the tests with `gradle check`.

### Project Template Credits

Thanks to [ae6rt](https://github.com/ae6rt/gradle-antlr4-template) for 
the antlr gradle project template.

A Gradle build template for ANTLR4 projects.  Just type "gradlew
build" to compile the grammar files and begin using in your src/main
and src/test source roots.

Remove the 

	apply from: file('gradle/idea.gradle')

if you are not using IntelliJ.  This is an IDE nice to have that
adds the generated sources to the IDE source roots.
