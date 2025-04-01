# ExpressoJ

<b>ExpressoJ</b> is a lightweight web framework for Java, inspired by Express.js. It simplifies backend development with an <b>embedded undertow server</b>, support for <b>middleware</b> and <b>standalone deployment</b> as an uber jar.

## Requirements

-   **Java**: Java 11 or higher.
-   **Maven**: Required to manage dependencies and build the project.

## Getting Started

To build the project using Maven, run the following command:

```sh
mvn clean install
```

Then, add the following dependency in your dependent projects <code>pom.xml</code> file.

```xml
</dependency>
    <groupId>com.spec.web</groupId>
    <artifactId>expressoj</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Starter Project

For a starter project, visit [**ExpressoJ-Starter**](https://github.com/neerajsurjaye/ExpressoJ-Starter). It provides a minimal example to get started quickly.

<picture>
    <source media="(prefers-color-scheme: dark)" srcset="./assets/logo/logo_black.png">
    <source media="(prefers-color-scheme: light)" srcset="./assets/logo/logo_light.png">
    <img alt="Logo" src="./assets/logo/logo_black.png">
</picture>
