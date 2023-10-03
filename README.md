# What is ESMAPPER?

ESMAPPER library contains an Annotation Processor that generates effective Java code 
to handle the mapping from one object (or objects) to other at compile time.

## Advantages of using ESMAPPER

   * mapper is generated during compilation
     * detected problem at compilation time

   * solving a lot of often problems as:
     * problem with cyclic dependencies in runtime
     * problem with object instances
     * problem with shared context data (as cache of instances)
     * problem with different but compatible types between internal and external sources (WebServices, JAXB, ...)
     * support for injection containers as CDI or Spring

## How to add it into project ?

Add those dependencies to your pom.xml
```xml
<dependencies>
     <!-- 
        for JDK v 11+  is 1.0.0
        for JDK v 8-10 is 1.0.0-jdk8 
     -->
     <properties>
          <esmapper.version>1.0.0</esmapper.version>
     </properties>
     
     <dependency>
         <groupId>com.esmo.mapper</groupId>
         <artifactId>esmapper-common</artifactId>
         <version>${esmapper.version}</version>
     </dependency>
     
     <dependency>
         <groupId>com.esmo.mapper</groupId>
         <artifactId>esmapper-processor</artifactId>
         <scope>provided</scope>
         <version>${esmapper.version}</version>
     </dependency>
</dependencies>
```

## How to create a mapper?
Define *Mapper* interface (or abstract class) describing the mapping you want. Follow example is with Spring support:

```java
@Mapper
@EnableSpring   // If you use Spring, generated interface is annotated for spring with @Component 
public interface SimpleMapper {
    
    // Imutable mapping
    OutBean map(InBean in);
    
    // Update graph
    OutBean update(InBean in, @Return OutBean out);
}
```

## How to use it?

Getting mapper instance directly:
```java
public class UsingMapper {

    public void main(String[] args) {
        SimpleMapper mapper = MapperUtil.getMapper(SimpleMapper.class);

        // example with immutable mapping
        OutBean res = mapper.map(in);
    }
}
```

Using mapper in Spring bean with @Autowired tag:
```java
@Component
public class AnySpringService {
    @Autowired
    private SimpleMapper mapper;

    public void example() {
        // example with immutable mapping
        OutBean res = mapper.map(in);
    
        // example with updating
        OutBean dest = dao.getById(42);
        OutBean res = mapper.update(in, dest);
        // res is the updated bean dest with in values
    }
}
```
