#스프링부트와 JPA 스터디

* 준비사항
  * 스프링부트 스타터(http://start.spring.io/)
  * 디펜던시: web, thymleaf, jpa, h2, lombok, validation
    * groupId : jpastudy
    * artifactId: demo
  * 스프링부트: 2.6.2 
  * Gradle Project
  * Packaging: Jar
---
  * [그림 1] 스프링이니셜라이즈 설정된 화면(http://start.spring.io/)
  * ![springio_setting](https://user-images.githubusercontent.com/5433728/148364927-3e907ac5-577e-4ef1-be5c-3619814ee78b.jpg "spring io 설정화면")
  
  ADD DEPENDENCIES... 버튼을 클릭해서 디펜던시를 선택하고 GENERATE 버튼을 클릭하여 zip 파일을 다운로드 받습니다. 

---
  build.graddle Graddle 전체 설정


```graddle
plugins {
	id 'org.springframework.boot' version '2.6.2'
	id 'io.spring.dependency-management' version '1.0.11.RELEASE'
	id 'java'
}
group = 'com.innotree.bcs.bp.study.jpa'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'
configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
}
repositories {
	mavenCentral()
}
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
	implementation 'org.springframework.boot:spring-boot-starter-validation'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.projectlombok:lombok'
	developmentOnly 'org.springframework.boot:spring-boot-devtools'
	runtimeOnly 'com.h2database:h2'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
test {
	useJUnitPlatform()
}
```
* 동작확인

앱어플리케이션 실행후 8080포트로 어플리케이션 기동된다. 부트는 최초 기동시 @SpringBootApplication 어노테이션이 있는 
파일을 확인하여 기동한다. 프로젝트를 생성하게 되면 자동생성되는 파일로 org.springframework.boot:spring-boot-starter-web을 
디펜던시로 추가하게 되면 톰캣을 기본으로 내장하여 기동하게 된다. 

```java
package com.innotree.bcs.bp.study.jpa.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
```


```java
C:\project\jdk-11.0.12\bin\java.exe -XX:TieredStopAtLevel=1 -noverify -Dspring.output.ansi.enabled=always "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2021.3\lib\idea_rt.jar=5523:C:\Program Files\JetBrains\IntelliJ IDEA 2021.3\bin" -Dcom.sun.management.jmxremote -Dspring.jmx.enabled=true -Dspring.liveBeansView.mbeanDomain -Dspring.application.admin.enabled=true -Dfile.encoding=UTF-8 -classpath C:\project\study\lab2\demo\build\classes\java\main;C:\project\study\lab2\demo\build\resources\main;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.projectlombok\lombok\1.18.22\9c08ea24c6eb714e2d6170e8122c069a0ba9aacf\lombok-1.18.22.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter-data-jpa\2.6.2\3eb637ccd03194f1d639b9efed1ff7d73c8592e3\spring-boot-starter-data-jpa-2.6.2.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter-thymeleaf\2.6.2\fa6c963c2537e5c1c0ed84dd9528062dba30e1a\spring-boot-starter-thymeleaf-2.6.2.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter-validation\2.6.2\3380b0ca1276496b2af424138db70c621e4a4d5\spring-boot-starter-validation-2.6.2.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter-web\2.6.2\685b236afc0144a52383cd2c52731dd016f9badd\spring-boot-starter-web-2.6.2.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter-aop\2.6.2\af16c0de56462ef8a6a9ac63cff30bf5120fc5b3\spring-boot-starter-aop-2.6.2.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter-jdbc\2.6.2\ff853babf286bf913d73197d0f0b521343d5e8aa\spring-boot-starter-jdbc-2.6.2.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\jakarta.transaction\jakarta.transaction-api\1.3.3\c4179d48720a1e87202115fbed6089bdc4195405\jakarta.transaction-api-1.3.3.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\jakarta.persistence\jakarta.persistence-api\2.2.3\8f6ea5daedc614f07a3654a455660145286f024e\jakarta.persistence-api-2.2.3.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.hibernate\hibernate-core\5.6.3.Final\a2420e0a2c9c168c029584aecf6a5b9a2475cd10\hibernate-core-5.6.3.Final.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework.data\spring-data-jpa\2.6.0\bd08ea8db76c7c82397307dda2e253180c31b7ec\spring-data-jpa-2.6.0.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework\spring-aspects\5.3.14\bf5de7c1338c2684d834bef8f389ef3d747f9f56\spring-aspects-5.3.14.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter\2.6.2\c36f1f6886cdbedc5347fdea62b97b44b053b0ba\spring-boot-starter-2.6.2.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.thymeleaf\thymeleaf-spring5\3.0.14.RELEASE\a0588f30a1e7dcadfc5c260ef6c6078ef377384\thymeleaf-spring5-3.0.14.RELEASE.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.thymeleaf.extras\thymeleaf-extras-java8time\3.0.4.RELEASE\36e7175ddce36c486fff4578b5af7bb32f54f5df\thymeleaf-extras-java8time-3.0.4.RELEASE.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.apache.tomcat.embed\tomcat-embed-el\9.0.56\8e4f28f714693ad4e158e61f41371d4e4c6b4e23\tomcat-embed-el-9.0.56.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.hibernate.validator\hibernate-validator\6.2.0.Final\d6b0760dfffbf379cedd02f715ff4c9a2e215921\hibernate-validator-6.2.0.Final.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter-json\2.6.2\c0d457ff259c487521706fd2efac75d61595d527\spring-boot-starter-json-2.6.2.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter-tomcat\2.6.2\81bc9a57c0df9787e122c3b2a66e5a1ac0e139a5\spring-boot-starter-tomcat-2.6.2.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework\spring-webmvc\5.3.14\beb6dc57abf6685878b824d8ab0af39ebd1cfbae\spring-webmvc-5.3.14.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework\spring-web\5.3.14\801d96f3914ace2e347ee3f6d29e21073e4f50ed\spring-web-5.3.14.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework\spring-aop\5.3.14\f049146a55991e89c0f04b9624f1f69e1763d80f\spring-aop-5.3.14.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.aspectj\aspectjweaver\1.9.7\158f5c255cd3e4408e795b79f7c3fbae9b53b7ca\aspectjweaver-1.9.7.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\com.zaxxer\HikariCP\4.0.3\107cbdf0db6780a065f895ae9d8fbf3bb0e1c21f\HikariCP-4.0.3.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework\spring-jdbc\5.3.14\f209e8d165dbcfc018aa4f741fbe75844eb45ff8\spring-jdbc-5.3.14.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.hibernate.common\hibernate-commons-annotations\5.1.2.Final\e59ffdbc6ad09eeb33507b39ffcf287679a498c8\hibernate-commons-annotations-5.1.2.Final.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.jboss.logging\jboss-logging\3.4.2.Final\e517b8a93dd9962ed5481345e4d262fdd47c4217\jboss-logging-3.4.2.Final.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\net.bytebuddy\byte-buddy\1.11.22\8b4c7fa5562a09da1c2a9ab0873cb51f5034d83f\byte-buddy-1.11.22.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\antlr\antlr\2.7.7\83cd2cd674a217ade95a4bb83a8a14f351f48bd0\antlr-2.7.7.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.jboss\jandex\2.2.3.Final\d3865101f0666b63586683bd811d754517f331ab\jandex-2.2.3.Final.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\com.fasterxml\classmate\1.5.1\3fe0bed568c62df5e89f4f174c101eab25345b6c\classmate-1.5.1.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.glassfish.jaxb\jaxb-runtime\2.3.5\a169a961a2bb9ac69517ec1005e451becf5cdfab\jaxb-runtime-2.3.5.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework\spring-context\5.3.14\ce6042492f042131f602bdc83fcb412b142bdac5\spring-context-5.3.14.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework\spring-orm\5.3.14\5ec15c098a56205ff77c7792e9dad30f5be16b5a\spring-orm-5.3.14.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework.data\spring-data-commons\2.6.0\5a9afaa6e0a4cd74183a794f467c9b4a546b4cbe\spring-data-commons-2.6.0.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework\spring-tx\5.3.14\3d80a1e051f071e9cd42fc99698bf9022862b5c\spring-tx-5.3.14.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework\spring-beans\5.3.14\24cc27af89edc1581a57bb15bc160d2353f40a0e\spring-beans-5.3.14.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework\spring-core\5.3.14\d87ad19f9d8b9a3f1a143db5a2be34c61751aaa2\spring-core-5.3.14.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.slf4j\slf4j-api\1.7.32\cdcff33940d9f2de763bc41ea05a0be5941176c3\slf4j-api-1.7.32.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-autoconfigure\2.6.2\7c91bce101d3f796cccbc1a6744c1ea389fff73f\spring-boot-autoconfigure-2.6.2.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot\2.6.2\bbf59f411320da665411692359ff511315d0ff91\spring-boot-2.6.2.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter-logging\2.6.2\58d4896c606b6ff07b9bd8e46c87eac5a51255de\spring-boot-starter-logging-2.6.2.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\jakarta.annotation\jakarta.annotation-api\1.3.5\59eb84ee0d616332ff44aba065f3888cf002cd2d\jakarta.annotation-api-1.3.5.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.yaml\snakeyaml\1.29\6d0cdafb2010f1297e574656551d7145240f6e25\snakeyaml-1.29.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.thymeleaf\thymeleaf\3.0.14.RELEASE\5ec84717bf76bcbcc133f9f19bab754f97b92f8\thymeleaf-3.0.14.RELEASE.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\jakarta.validation\jakarta.validation-api\2.0.2\5eacc6522521f7eacb081f95cee1e231648461e7\jakarta.validation-api-2.0.2.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\com.fasterxml.jackson.datatype\jackson-datatype-jsr310\2.13.1\1ece5a87b59701328215e0083448b4d451857cbd\jackson-datatype-jsr310-2.13.1.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\com.fasterxml.jackson.module\jackson-module-parameter-names\2.13.1\cbeec2259213c555ef451a2e05f35ed1dbfbf799\jackson-module-parameter-names-2.13.1.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\com.fasterxml.jackson.datatype\jackson-datatype-jdk8\2.13.1\8ecfa9bcd714269fdf22c33f9fd00d0643bd0e21\jackson-datatype-jdk8-2.13.1.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\com.fasterxml.jackson.core\jackson-databind\2.13.1\698b2d2b15d9a1b7aae025f1d9f576842285e7f6\jackson-databind-2.13.1.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.apache.tomcat.embed\tomcat-embed-websocket\9.0.56\d84be683a5d47e820d077db1d511181c7db9e4e9\tomcat-embed-websocket-9.0.56.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.apache.tomcat.embed\tomcat-embed-core\9.0.56\7c8e0008564c644beec976ab115e2670bb4d7003\tomcat-embed-core-9.0.56.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework\spring-expression\5.3.14\5cd4c568522b7084afac5d2ac6cb945b797b3f16\spring-expression-5.3.14.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\jakarta.xml.bind\jakarta.xml.bind-api\2.3.3\48e3b9cfc10752fba3521d6511f4165bea951801\jakarta.xml.bind-api-2.3.3.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.glassfish.jaxb\txw2\2.3.5\ec8930fa62e7b1758b1664d135f50c7abe86a4a3\txw2-2.3.5.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\com.sun.istack\istack-commons-runtime\3.0.12\cbbe1a62b0cc6c85972e99d52aaee350153dc530\istack-commons-runtime-3.0.12.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework\spring-jcl\5.3.14\ffcf745ed5ba32930771378316fd08e97986bec2\spring-jcl-5.3.14.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\ch.qos.logback\logback-classic\1.2.9\7d495522b08a9a66084bf417e70eedf95ef706bc\logback-classic-1.2.9.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.apache.logging.log4j\log4j-to-slf4j\2.17.0\e50b82411b9ce9c204c938509f914b2bb887168b\log4j-to-slf4j-2.17.0.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.slf4j\jul-to-slf4j\1.7.32\8a055c04ab44e8e8326901cadf89080721348bdb\jul-to-slf4j-1.7.32.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.attoparser\attoparser\2.0.5.RELEASE\a93ad36df9560de3a5312c1d14f69d938099fa64\attoparser-2.0.5.RELEASE.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.unbescape\unbescape\1.1.6.RELEASE\7b90360afb2b860e09e8347112800d12c12b2a13\unbescape-1.1.6.RELEASE.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\com.fasterxml.jackson.core\jackson-annotations\2.13.1\1cbcbe4623113e6af92ccaa89884a345270f1a87\jackson-annotations-2.13.1.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\com.fasterxml.jackson.core\jackson-core\2.13.1\51ae921a2ed1e06ca8876f12f32f265e83c0b2b8\jackson-core-2.13.1.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\ch.qos.logback\logback-core\1.2.9\cdaca0cf922c5791a8efa0063ec714ca974affe3\logback-core-1.2.9.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.apache.logging.log4j\log4j-api\2.17.0\bbd791e9c8c9421e45337c4fe0a10851c086e36c\log4j-api-2.17.0.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-devtools\2.6.2\2f766b028fcb09d6b3fec1f17bcc19b96ff4ebce\spring-boot-devtools-2.6.2.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\com.h2database\h2\1.4.200\f7533fe7cb8e99c87a43d325a77b4b678ad9031a\h2-1.4.200.jar;C:\Users\LG\.gradle\caches\modules-2\files-2.1\com.sun.activation\jakarta.activation\1.2.2\74548703f9851017ce2f556066659438019e7eb5\jakarta.activation-1.2.2.jar com.innotree.bcs.bp.study.jpa.demo.DemoApplication
19:40:25.144 [Thread-0] DEBUG org.springframework.boot.devtools.restart.classloader.RestartClassLoader - Created RestartClassLoader org.springframework.boot.devtools.restart.classloader.RestartClassLoader@7be702c1

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.6.2)

2022-01-06 19:40:25.529  INFO 7116 --- [  restartedMain] c.i.b.bp.study.jpa.demo.DemoApplication  : Starting DemoApplication using Java 11.0.12 on DESKTOP-22HNTFN with PID 7116 (C:\project\study\lab2\demo\build\classes\java\main started by LG in C:\project\study\lab2\demo)
2022-01-06 19:40:25.530  INFO 7116 --- [  restartedMain] c.i.b.bp.study.jpa.demo.DemoApplication  : No active profile set, falling back to default profiles: default
2022-01-06 19:40:25.578  INFO 7116 --- [  restartedMain] .e.DevToolsPropertyDefaultsPostProcessor : Devtools property defaults active! Set 'spring.devtools.add-properties' to 'false' to disable
2022-01-06 19:40:25.578  INFO 7116 --- [  restartedMain] .e.DevToolsPropertyDefaultsPostProcessor : For additional web related logging consider setting the 'logging.level.web' property to 'DEBUG'
2022-01-06 19:40:26.242  INFO 7116 --- [  restartedMain] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2022-01-06 19:40:26.253  INFO 7116 --- [  restartedMain] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 4 ms. Found 0 JPA repository interfaces.
2022-01-06 19:40:26.654  INFO 7116 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2022-01-06 19:40:26.662  INFO 7116 --- [  restartedMain] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2022-01-06 19:40:26.662  INFO 7116 --- [  restartedMain] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.56]
2022-01-06 19:40:26.726  INFO 7116 --- [  restartedMain] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2022-01-06 19:40:26.727  INFO 7116 --- [  restartedMain] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1149 ms
2022-01-06 19:40:26.747  INFO 7116 --- [  restartedMain] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2022-01-06 19:40:26.821  INFO 7116 --- [  restartedMain] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2022-01-06 19:40:26.828  INFO 7116 --- [  restartedMain] o.s.b.a.h2.H2ConsoleAutoConfiguration    : H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:86f5487d-bb85-410d-91ac-f96a220c60b5'
2022-01-06 19:40:26.904  INFO 7116 --- [  restartedMain] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
2022-01-06 19:40:26.942  INFO 7116 --- [  restartedMain] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 5.6.3.Final
2022-01-06 19:40:27.034  INFO 7116 --- [  restartedMain] o.hibernate.annotations.common.Version   : HCANN000001: Hibernate Commons Annotations {5.1.2.Final}
2022-01-06 19:40:27.119  INFO 7116 --- [  restartedMain] org.hibernate.dialect.Dialect            : HHH000400: Using dialect: org.hibernate.dialect.H2Dialect
2022-01-06 19:40:27.244  INFO 7116 --- [  restartedMain] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
2022-01-06 19:40:27.251  INFO 7116 --- [  restartedMain] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2022-01-06 19:40:27.291  WARN 7116 --- [  restartedMain] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
2022-01-06 19:40:27.470  WARN 7116 --- [  restartedMain] org.thymeleaf.templatemode.TemplateMode  : [THYMELEAF][restartedMain] Template Mode 'HTML5' is deprecated. Using Template Mode 'HTML' instead.
2022-01-06 19:40:27.527  INFO 7116 --- [  restartedMain] o.s.b.d.a.OptionalLiveReloadServer       : LiveReload server is running on port 35729
2022-01-06 19:40:27.553  INFO 7116 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2022-01-06 19:40:27.563  INFO 7116 --- [  restartedMain] c.i.b.bp.study.jpa.demo.DemoApplication  : Started DemoApplication in 2.405 seconds (JVM running for 4.59)

```


# 제목 1
## 제목 2
### 제목 3
#### 제목 4
##### 제목 5
###### 제목 6

제목 1
======

제목 2
------

이텔릭체는 *별표(asterisks)* 혹은 _언더바(underscore)_를 사용하세요.
두껍게는 **별표(asterisks)** 혹은 __언더바(underscore)__를 사용하세요.
**_이텔릭체_와 두껍게**를 같이 사용할 수 있습니다.
취소선은 ~~물결표시(tilde)~~를 사용하세요.
<u>밑줄</u>은 `<u></u>`를 사용하세요.

1. 순서가 필요한 목록
1. 순서가 필요한 목록
- 순서가 필요하지 않은 목록(서브)
- 순서가 필요하지 않은 목록(서브)
1. 순서가 필요한 목록
1. 순서가 필요한 목록(서브)
1. 순서가 필요한 목록(서브)
1. 순서가 필요한 목록

- 순서가 필요하지 않은 목록에 사용 가능한 기호
    - 대쉬(hyphen)
    * 별표(asterisks)
    + 더하기(plus sign)

[GOOGLE](https://google.com)

[NAVER](https://naver.com "링크 설명(title)을 작성하세요.")

[상대적 참조](../users/login)

[Dribbble][Dribbble link]

[GitHub][1]

문서 안에서 [참조 링크]를 그대로 사용할 수도 있습니다.

다음과 같이 문서 내 일반 URL이나 꺾쇠 괄호(`< >`, Angle Brackets)안의 URL은 자동으로 링크를 사용합니다.
구글 홈페이지: https://google.com
네이버 홈페이지: <https://naver.com>

[Dribbble link]: https://dribbble.com
[1]: https://github.com
[참조 링크]: https://naver.com "네이버로 이동합니다!"


![대체 텍스트(alternative text)를 입력하세요!](![springio_setting](https://user-images.githubusercontent.com/5433728/148364927-3e907ac5-577e-4ef1-be5c-3619814ee78b.jpg) "링크 설명(title)을 작성하세요.")

![Kayak][logo]

[logo]: http://www.gstatic.com/webp/gallery/2.jpg "To go kayaking."

[![Vue](/images/vue.png)](https://kr.vuejs.org/)


```html
<a href="https://www.google.co.kr/" target="_blank">GOOGLE</a>
```

```css
.list > li {
  position: absolute;
  top: 40px;
}
```

```javascript
function func() {
  var a = 'AAA';
  return a;
}
```

```bash
$ vim ./~zshrc
```

```python
s = "Python syntax highlighting"
print s
```

```
No language indicated, so no syntax highlighting. 
But let's throw in a tag.
```

``` java
public class HelloWorld {
    public void hello() {
        system.out.println("Hello");
}
```

| 값 | 의미 | 기본값 |
|---|:---:|---:|
| `static` | 유형(기준) 없음 / 배치 불가능 | `static` |
| `relative` | 요소 자신을 기준으로 배치 |  |
| `absolute` | 위치 상 부모(조상)요소를 기준으로 배치 |  |
| `fixed` | 브라우저 창을 기준으로 배치 |  |


값 | 의미 | 기본값
---|:---:|---:
`static` | 유형(기준) 없음 / 배치 불가능 | `static`
`relative` | 요소 **자신**을 기준으로 배치 |
`absolute` | 위치 상 **_부모_(조상)요소**를 기준으로 배치 |
`fixed` | **브라우저 창**을 기준으로 배치 |



인용문(blockQuote)

> 남의 말이나 글에서 직접 또는 간접으로 따온 문장.
> _(네이버 국어 사전)_

BREAK!

> 인용문을 작성하세요!
>> 중첩된 인용문(nested blockquote)을 만들 수 있습니다.
>>> 중중첩된 인용문 1
>>> 중중첩된 인용문 2
>>> 중중첩된 인용문 3


<u>마크다운에서 지원하지 않는 기능</u>을 사용할 때 유용하며 대부분 잘 동작합니다.

<img width="150" src="http://www.gstatic.com/webp/gallery/4.jpg" alt="Prunus" title="A Wild Cherry (Prunus avium) in flower">

![Prunus](http://www.gstatic.com/webp/gallery/4.jpg)


---
(Hyphens)

***
(Asterisks)

___
(Underscores)


동해물과 백두산이 마르고 닳도록
하느님이 보우하사 우리나라 만세   <!--띄어쓰기 2번-->
무궁화 삼천리 화려 강산<br>
대한 사람 대한으로 길이 보전하세