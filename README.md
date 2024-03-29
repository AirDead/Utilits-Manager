# Utilities-Manager

## Import to project:

### Maven:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.AirDead</groupId>
    <artifactId>Utilities-Manager</artifactId>
    <version>1.1.2</version>
</dependency>
```

### Grade:

```xml
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.AirDead:Utilities-Manager:1.1.2'
}
```
