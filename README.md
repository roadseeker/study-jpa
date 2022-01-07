# 스프링부트와 JPA 스터디

_세미나 자료는 김영환님(인프런 JPA 강의자)의 자료를 참조하여 작성하였습니다._

_앞으로의 계획_
1. _스프링부트와 JPA_ <- 금번 세미나
2. _스프링부트와 RestAPI 개발_ 
   - _스프링부트와 JPA로 개발된 내용을 RestAPI로 어떻게 변환해야 좋은 API 설계인지 확인_
3. _스프링 데이타 JAP와 QureyDSL적용_
4. _마이크로 서비스 개발_
   - _Spring boot, Spring Data JPA, RestAPI로 개발된 내용을 마이크로서비스 전환_
5. _Docker와 Kuberneties_ 
   - _마이크로 서비스로 개발 된 내용을 Docker와 Kubeneties 환경으로 구현_ 
   


## 준비사항
  * 스프링부트 스타터(http://start.spring.io/)를 이용해서 프로젝트 생성한다
    * java: 11
    * IDE: 편한것(Eclips, InteliJ)
    * 디펜던시: web, thymeleaf, jpa, h2, lombok, validation
      * groupId : com.innotree.bcs.bp.study.jpa
      * artifactId: demo
    * 스프링부트: 2.6.2 
    * Gradle Project
    * Packaging: Jar

  * [그림 1] 스프링이니셜라이즈 설정 화면(http://start.spring.io/)

![springio_setting](https://user-images.githubusercontent.com/5433728/148364927-3e907ac5-577e-4ef1-be5c-3619814ee78b.jpg "spring io 설정화면")

**ADD DEPENDENCIES...** 버튼을 클릭해서 web, developer tools, jpa, h2, lombok, thymeleaf
디펜던시를 선택하고 **GENERATE** 버튼을 클릭하여 zip 파일을 다운로드 받는다.
다운로드 받은 zip 파일을 해제하고 IDE로 임포트하면 그래들 프로젝트가 만들어진다. 


* build.graddle Graddle 전체 설정

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
---
## DomoApplication 동작확인

앱어플리케이션 실행후 8080포트로 어플리케이션 기동된다. 부트는 최초 기동시 @SpringBootApplication 어노테이션이 있는 
파일을 확인하여 기동한다. 

org.springframework.boot:spring-boot-starter-web을 디펜던시로 추가하게 되면 톰캣을 기본으로 내장하여 기동하게 되어
웹환경에서 개발을 진행할 수 없다.

### Spring Boot의 장점
1) 라이브러리 관리 자동화
- 기존 스프링 자바 프로젝트에서는 메이븐이나 그래들을 이용해서 라이브러리 의존성을 관리해왔다. 
- 하지만 스프링 부트에서는 스타터(Starter)라는 것을 이용해 특정 기능에 필요한 라이브러리 의존성을 더욱 간단히 처리 할 수 있다.

2) 설정의 자동화
- 스프링 부트에서는 프로젝트에 추가된 라이브러리를 기반으로 실행에 필요한 환경을 자동으로 설정해준다.
- 개발에 필요한 라이브러리들을 추가하면 스픵 부트가 이 라이브러리들을 인지해서 관련된 스프링 설정을 자동으로 
처리해주기 때문에 **개발자들은 복잡한 설정을 하지 않고도 개발**이 가능하다.


3) 라이브러리 버전 자동 관리
- 개발시 가장 신경쓰이는 부분이 라이브러리와 버젼 관리이다. 기존의 스프링은 스프링 라이브러리만 사용하여 개발할 수 없으며, 
의존관계에 있는 서드파티 라이브러들도 사용한다. 스프링 부트를 사용하면 스프링 부트 버전에 해당하는 스프링 라이브러리뿐만 아니라 
서드파티 라이브러리들도 호환되는 버전으로 다운로드해준다.
- 라이브러리 버전이 달라 정상적으로 동작하지 않는 상황을 겪을 필요가 없고, XML설정을 이용해서 라이브러리를 매번 설정하는
과정을 줄이고 **개발에만 집중할 수 있는 환경**을 제공한다.


4) 테스트 환경과 내장 Tomcat
- JUnit을 비롯한 테스트 관련 라이브러리들이 기본적으로 포함되어 있기 때문에 컨트롤러를 비롯한 다양한 계층의 클래스들에 
대해서 테스트 케이스를 쉽게 작성할 수 있다.
- Tomcat 서버를 내장하고 있기 때문에 단지 main() 메소드를 가진 클래스를 실행하는 방식으로 서버를 구동하기 때문에 
실행결과를 빠르게 확인할 수 있다.


5) 독립적으로 실행 가능한 JAR
- 애플리케이션을 개발하고 테스트까지 마쳤으면 애플리케이션을 실제 운영 서버에 배포하기 위해서 패키징을 해야하는데, 
프로젝트가 일반 자바 프로젝트라면 간단하게 JAR파일로 패키징하면 되지만 웹 프로젝트라면 WAR 파일로 패키징 해야한다.

- 스프링 부트는 독립적으로 실행 가능한 애플리케이션을 빠르게 개발하는 것을 목표로 하기 때문에 웹 애플리케이션도 WAR가 아닌 
**JAR파일로 패키징 하여 사용**할 수 있다.

[그림 2] IDE에 프로젝트 임포트
![graddle_project](https://user-images.githubusercontent.com/5433728/148400199-dcb90beb-06e9-4ef1-a345-4a29a09ca112.jpg)

* 부트기동

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


```
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
---
## 스프링 부트 thymeleaf viewName 매핑

application.properties --> application.yml 파일로 변경 야믈파일이 프로퍼티 파일보다 간결하여 요즘 선호되고 있다.
yml 파일에 thymeleaf 관련 설정을 등록한다.

resources:templates/ +{ViewName}+ .html 와 같이 뷰를 호출할 수 있도록 설정된다. ViewName이 home으로 지정되는 경우 
다음과 같이 변경되어 호출된다. resources:templates/home.html

````yaml
spring:
  thymeleaf:
    prefix: classpath:/templates/
    suffix: .html

````
[그림 3] 프로젝트 구조
![project_structure](https://user-images.githubusercontent.com/5433728/148375886-e966c17e-874b-4a56-b360-5ab0b802fa57.jpg)

* 타임리프 템플릿 등록 
1. home.html 

```html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head th:replace="fragments/header :: header">
    <title>Hello</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<div class="container">
    <div th:replace="fragments/bodyHeader :: bodyHeader" />
    <div class="jumbotron">
        <h1>HELLO SHOP</h1>
        <p class="lead">회원 기능</p>
        <p>
            <a class="btn btn-lg btn-secondary" href="/members/new">회원 가입</a>
            <a class="btn btn-lg btn-secondary" href="/members">회원 목록</a>
        </p>
        <p class="lead">상품 기능</p>
        <p>
            <a class="btn btn-lg btn-dark" href="/items/new">상품 등록</a>
            <a class="btn btn-lg btn-dark" href="/items">상품 목록</a>
        </p>
        <p class="lead">주문 기능</p>
        <p>
            <a class="btn btn-lg btn-info" href="/order">상품 주문</a>
            <a class="btn btn-lg btn-info" href="/orders">주문 내역</a>
        </p>
    </div>
    <div th:replace="fragments/footer :: footer" />
</div> <!-- /container -->
```
2. templates/header
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head th:fragment="header">
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrinkto-
fit=no">
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  <!-- Custom styles for this template -->
  <link href="/css/jumbotron-narrow.css" rel="stylesheet">
  <title>Hello, world!</title>
</head>
```
3. templates/bodyheader
````html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<div class="header" th:fragment="bodyHeader">
    <ul class="nav nav-pills pull-right">
        <li><a href="/">Home</a></li>
    </ul>
    <a href="/"><h3 class="text-muted">HELLO SHOP</h3></a>
</div>
````

4. templates/footer
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<div class="footer" th:fragment="footer">
    <p>&copy; Hello Shop V2</p>
</div>
```
5. resources/static/css/jumbotron-narrow.css 추가
````css
/* Space out content a bit */
body {
    padding-top: 20px;
    padding-bottom: 20px;
}
/* Everything but the jumbotron gets side spacing for mobile first views */
.header,
.marketing,
.footer {
    padding-left: 15px;
    padding-right: 15px;
}
/* Custom page header */
.header {
    border-bottom: 1px solid #e5e5e5;
}
/* Make the masthead heading the same height as the navigation */
.header h3 {
    margin-top: 0;
    margin-bottom: 0;
    line-height: 40px;
    padding-bottom: 19px;
}
/* Custom page footer */
.footer {
    padding-top: 19px;
    color: #777;
    border-top: 1px solid #e5e5e5;
}
/* Customize container */
@media (min-width: 768px) {
    .container {
        max-width: 1200px;
    }
}
.container-narrow > hr {
    margin: 30px 0;
}
/* Main marketing message and sign up button */
.jumbotron {
    text-align: center;
    border-bottom: 1px solid #e5e5e5;
}
.jumbotron .btn {
    font-size: 21px;
    padding: 14px 24px;
}
/* Supporting marketing content */
.marketing {
    margin: 40px 0;
}
.marketing p + h4 {
    margin-top: 28px;
}
/* Responsive: Portrait tablets and up */
@media screen and (min-width: 768px) {
    /* Remove the padding we set earlier */
    .header,
    .marketing,
    .footer {
        padding-left: 0;
        padding-right: 0;
    }
    /* Space out the masthead */
    .header {
        margin-bottom: 30px;
    }
    /* Remove the bottom border on the jumbotron for visual effect */
    .jumbotron {
        border-bottom: 0;
    }
}
````
* HomeController 코딩
```java
package com.innotree.bcs.bp.study.jpa.demo.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {
    @GetMapping(value = "/")
    public String home(Model model){
        log.info("home controller");
        return "home";
    }
}
```
* 화면 확인
[그림 4] DEMO shop 
![home_view](https://user-images.githubusercontent.com/5433728/148380576-b1b7f105-9960-4757-ac8d-b6b4f64f148a.jpg)

---
## H2 DB 설치
* H2 데이터베이스는 개발이나 테스트용도로 가볍고 편리한 DB

H2 Database 다운로드 사이트(https://www.h2database.com)

최신버전을 다운로드 받아서 사용한다. 현재는 2.0.206(2022-01-04) 버전이다.

링크: https://github.com/h2database/h2database/releases/download/version-2.0.206/h2-2022-01-04.zip

다운받은 후 원하는 위치에 압축을 풀고 ./h2/bin 디렉토리의 h2.bat을 실행한다.(리눅스: h2.sh)
실행 후 웹브라우저가 자동실행되어 접속설정창이 열린다.(http://localhost:8082/login.do)

* 데이터베이스 파일 생성 방법

    * jdbc:h2:&#126;/demoshop (접속 창에서 최소 한번 실행하여 db를 생성한다.)
    * 파일 생성 확인: 윈도우 자신의 계정 폴더에서 demoshop.mv.db
    * 이후 부터는 jdbc:h2:tcp://localhost/&#126;/demoshop

[그림 5 H2db 접속]  
![h2db_connect](https://user-images.githubusercontent.com/5433728/148383753-c5ca6e6c-6f66-447e-85ab-f7702564b6ab.jpg)

[그림 6 H2db 개발환경]
![h2database](https://user-images.githubusercontent.com/5433728/148383560-21dfbecf-dbaf-4823-84cd-58fe706fbae2.jpg)

* JPA와 DB연결 설정 및 동작 확인

application.yml 설정
```yaml
spring:
  thymeleaf:
    prefix: classpath:/templates/
    suffix: .html
  datasource:
    url: jdbc:h2:tcp://localhost/~/demoshop
    username: sa
    password:
    driver-class-name: org.h2.Driver
  jpa:
    hibernate:
      ddl-auto: create
    properties:
      hibernate:
        format_sql: true
logging:
  level:
    org.hibernate.SQL: debug
```
* spring.jpa.hibernate.ddl-auto: create  이 옵션은 애플리케이션 실행 시점에 테이블을 drop 하고, 다시 생성한다.

* org.hibernate.SQL: true 옵션은 logger를 통해 하이버네이트 실행 SQL을 남긴다.

잘 작동되는지를 확인하기 위해 회원엔티티, 회원레포지토리를 작성하고 테스트를 수행한다.


1. 회원엔티티
```java
package com.innotree.bcs.bp.study.jpa.demo.member.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String name;
}
```
2. 회원레포지토리
```java
package com.innotree.bcs.bp.study.jpa.demo.member.repository;

import com.innotree.bcs.bp.study.jpa.demo.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member findOne(Long memberId){
        return em.find(Member.class, memberId);
    }
}
```
3. 테스트(src에 main과 test 폴더가 존재하는데 테스트파일은 test폴더 아래에 만든다)
````java
package com.innotree.bcs.bp.study.jpa.demo.member.repository;

import com.innotree.bcs.bp.study.jpa.demo.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired MemberRepository repository;

    @Test
    @DisplayName(value = "회원저장")
    @Transactional
    @Rollback(value = false)
    public void save() {
        Member member = new Member();
        member.setName("lee kyoungik");
        Long savedId = repository.save(member);

        Member findMember = repository.findOne(savedId);

        assertThat(savedId).isEqualTo(findMember.getId());
        assertThat(findMember.getName()).isEqualTo(member.getName());
        assertThat(findMember).isEqualTo(member);
    }
}
````
[그림 7 테스트성공 확인]

![jap_db_integration_success](https://user-images.githubusercontent.com/5433728/148395572-a7e4ceed-127b-443e-a43f-a2eaa494294b.jpg)

지금까지가 스프링부트환경에서 JPA demo를 개발하기 위한 환경 구성이다. 앞으로 구성해야할 화면의 메인과 JPA의 작동을 확인해보았다.
다음으로 JAP에 대해서 알아본다.

## JPA가 영속성(Persistance)을 관리하는 방법

### JPA에서 중요한 2가지
* 영속성 컨텍스트 이해
* 객체와 관계형 데이터베이스 매핑하기 (Entity를 잘 설계하는 것)

#### 영속성 컨텍스트란
* 영속성(Persistence)을 관리하는 환경을 말한다.
![jpa_context](https://user-images.githubusercontent.com/5433728/148476143-ff782ab1-26d9-4d4c-bcaf-fda0b80e92b8.jpg)
 
* 엔티티를 저장하게 되면 영속컨텍스의 entityManager의 캐시에 저장된다
````java
//엔티티를 생성한 상태(비영속)
Member member = new Member();
member.setId("member1");
member.setUsername("회원1");
//엔티티를 영속
em.persist(member);
```` 
![entityManager_persist](https://user-images.githubusercontent.com/5433728/148479048-2fe3d072-1ff0-44ff-9518-7d5100d6ae1c.jpg)

* 엔티티를 조회하게되면 entityManger의 캐시에서 찾는다
```java
Member member = new Member();
member.setId("member1");
member.setUsername("회원1");
//1차 캐시에 저장됨
em.persist(member);
//1차 캐시에서 조회
Member findMember = em.find(Member.class, "member1");
```
![entityManger_find](https://user-images.githubusercontent.com/5433728/148479275-c855e858-1d83-49fa-b4e0-219599933b1c.jpg)

* 1차캐시에 엔티티가 없는 경우 EntityManger는 DB에 쿼리하여 1차캐시에 저장하고 객체를 반환한다.
````java
Member findMember2 = em.find(Member.class, "member2");
````
  ![entityManager_db_find](https://user-images.githubusercontent.com/5433728/148479437-1bd8a2f4-d468-4766-9437-145da064c86d.jpg)
* 1차캐시를 사용하게 됨으로써 쓰기지연이 가능하다
````java
EntityManager em = emf.createEntityManager();
EntityTransaction transaction = em.getTransaction();
//엔티티 매니저는 데이터 변경시 트랜잭션을 시작해야 한다.
transaction.begin(); // [트랜잭션] 시작
em.persist(memberA);
em.persist(memberB);
//여기까지 INSERT SQL을 데이터베이스에 보내지 않는다.
//커밋하는 순간 데이터베이스에 INSERT SQL을 보낸다.
transaction.commit(); // [트랜잭션] 커밋
````
![entityManager_lazy_write](https://user-images.githubusercontent.com/5433728/148479893-662745b8-81e3-4c4c-b7b7-5b527321955a.jpg)
* taransaction.commit()
![entityManager_lazy_write_commit](https://user-images.githubusercontent.com/5433728/148480057-60b78c15-561c-43aa-bb0b-a6701999ed67.jpg)

* 엔티티가 수정된 경우 entityManger의 변경감지 기능이 동작된다. 변경감지(Dirty Checking)는 변경된 내용을
쓰기지연 SQL 저장소에 저장하였다가 flush() 요청이 있는 경우 데이터베이스에 해당 내용을 기록하고 커밋한다.
```java
EntityManager em = emf.createEntityManager();
EntityTransaction transaction = em.getTransaction();
transaction.begin(); // [트랜잭션] 시작
// 영속 엔티티 조회
Member memberA = em.find(Member.class, "memberA");
// 영속 엔티티 데이터 수정
memberA.setUsername("hi");
memberA.setAge(10);
//em.update(member) 이런 코드가 있어야 하지 않을까?
transaction.commit(); // [트랜잭션] 커밋
```
![entityManager_dirty_checking](https://user-images.githubusercontent.com/5433728/148480506-ce5f896e-0c5e-4164-b994-0710cef952b0.jpg)
* 플러쉬란
  * 영속성 컨텍스트의 변경내용을 데이터베이스에 반영하는 것을 말한다.
* 플러시의 발생 순서
  * 변경감지
  * 수정된 엔티티 쓰기 지연 SQL 저장소에 등록
  * 쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송(등록, 수정, 삭제 쿼리)
* 영속성 컨텍스트를 플러시하는 방법
  * em.flush() - 직접호출
  * 트랜잭션커밋 - 플러시 자동 호출
  * JPQL 쿼리 실행 - 플러시 자동 호출
    * JPQL 쿼리 실행시 플러시가 자동으로 호출되는 이유는 JPQL은 DB에 직접 조회하기 때문에 영속성컨텍스트에서
아직 커밋되지 않는 엔티티들은 조회되지 않기 때문이다.
```java
em.persist(memberA);
em.persist(memberB);
em.persist(memberC);
//중간에 JPQL 실행
query = em.createQuery("select m from Member m", Member.class);
List<Member> members= query.getResultList(); //memberA, memberB, memberC는 조회되지 않음
```
### Entity 필드와 컬럼매핑

```java
package com.innotree.bcs.bp.study.jpa.demo.member.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;
}
```

#### 기본 키 매핑
 * 직접 할당: @Id만 사용
 * 자동 생성(@GeneratedValue)
   * IDENTITY: 데이터베이스에 위임, MYSQL 
   * SEQUENCE: 데이터베이스 시퀀스 오브젝트 사용, ORACLE 
     * @SequenceGenerator 필요 
   * TABLE: 키 생성용 테이블 사용, 모든 DB에서 사용 
     * @TableGenerator 필요 
   * AUTO: 방언에 따라 자동 지정, 기본값
 ##### IDENTITY전략 - 특징
   * 기본 키 생성을 데이터베이스에 위임
   * 주로 MySQL, PostgreSQL, SQL Server, DB@에서 사용
     * (예: MySQL의 AUTO_INCREMENT)
   * JPA는 보통 트랜잭션 커밋 시점에 INSERT SQL 실행
   * AUTO_INCREMENT는 데이터베이스에 INSERT SQL을 실행한 이후에 ID값을 알수 있음
   * 보통은 트랜잭션종료시점에 INSERT SQL이 실행되나 entityManager가 엔티티의 식별자로 id값을 사용하므로 INDENTITY 전략때에는 em.persist() 시점에 즉시 INSERT SQL 실행한다.
   *

##### IDENTITY전략 - 매핑
````java
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
````
##### SEQUENCE 전략 - 특징
* 데이터베이스 시퀀스는 유일한 값을 순서대로 생성하는 특별한
  데이터베이스 오브젝트(예: 오라클 시퀀스)
* 오라클, PostgreSQL, DB2, H2 데이터베이스에서 사용

##### SEQUENCE 전략 - 매핑
```java
package com.innotree.bcs.bp.study.jpa.demo.member.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ",
        initialValue = 1,
        allocationSize = 1)
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "MEMBER_SEQ_GENERATOR")
    private Long id;
}
```

##### SEQUENCE - @SequenceGenerator


| 속성                      |                              설명                              | 기본값 |
|-------------------------|:------------------------------------------------------------:|:----|
| `name`                  |                          식별자 생성기 이름                          | 필수  |
| `sequenceName`          |                    데이터베이스에 등록되어 있는 시퀀스 이름                    ||
| `initialValue`          |      DDL 생성 시에만 사용됨, 시퀀스 DDL을 생성할 때 처음 1로 시작하는 수를 지정한다.      ||
| `unique(DDL)`           |  ||
| `colunmDefinition(DDL)` | 데이터베이스 컬럼정보를 직접 줄수 있다<br/>ex) varchar(100), default 'EMPTY'  |     |
| `length(DDL)`           |                문자 길이 제약조건, String 타입에만 사용한다.                 | 255 |



#### 매핑 어노테이션 정리
| 어노테이션|            설정            |
|------|:------------------------:|
| `@Column` |          컴럼 매핑           | 
| `@Temporal` |         날자 타입 매핑         |
| `@Enumerated` |        enum 타입 매핑        |
| `@Lob` |      BLOB, CLOB 매핑       |
| `@Transient` | 특정 필드를 컬럼에 매핑하지 않음(매핑무시) |



#### @Column

| 속성                         |                                                                              설명                                                                              | 기본값                        |
|----------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------:|:---------------------------|
| `name`                     |                                                                         필드와 매핑할 컬럼이름                                                                         | true                       |
| `insertable<br/>updatable` |                                                                         등록, 변경 가능 여부                                                                         ||
| `nullable(DDL)`            |                                                 null 값의 허용 여부 설정, false로 설정하면<br/>DDL생성시에not null 제약조건이 붙는다                                                  ||
| `unique(DDL)`                   |                                                 @Table의 uniqueConstraints와 같지만 한 컬럼에 간단히 유니크 제약조건을 걸 때 새용한다.                                                 ||
| `colunmDefinition(DDL)`         |                                                 데이터베이스 컬럼정보를 직접 줄수 있다<br/>ex) varchar(100), default 'EMPTY'                                                  |                            |
| `length(DDL)`                   |                                                                문자 길이 제약조건, String 타입에만 사용한다.                                                                 | 255                        |
| `precision, scale(DDL)`         | BigDecimal 타입에서 사용한다(BigInteger도 사용할 수 있다). precision은 소수점을 포함한 전체 자릿수를, scale은 소수의 자릿수다. 참고로 double, float 타입에는 적용되지 않는다. 아주 큰 숫자나 정밀한 소수를 다루어야 할 때만 사용한다.  | precision=19, <br/>scale=2 |



스프링부트에서는 JPA(

앞으로는 고객의 요구사항을 분석하여 도메인을 설계하고 도메인을 바탕으로 JPA이용하여 기능을 구헌하면서 JPA 구조와
사용방법을 알아보도록 하겠다.
---

## 요구사항
* 기능 목록
* 회원 기능
  * 회원 등록
  * 회원 조회
*상품 기능
  * 상품 등록
  * 상품 수정
  * 상품 조회
* 주문 기능
  * 상품 주문
  * 주문 내역 조회
  * 주문 취소
* 기타 요구사항
  * 상품은 재고 관리가 필요하다.
  * 상품의 종류는 도서, 음반, 영화가 있다.
  * 상품을 카테고리로 구분할 수 있다.
  * 상품 주문시 배송 정보를 입력할 수 있다.

### 도메인 모델과 테이블 설계
![domain_model](https://user-images.githubusercontent.com/5433728/148403404-08ae7dc1-edc9-4079-a3af-fc7fe85c7596.jpg)

**회원, 주문, 상품의 관계:** 회원은 여러 상품을 주문할 수 있다. 그리고 한 번 주문할 때 여러 상품을 선택할 수
있으므로 주문과 상품은 다대다 관계다. 하지만 이런 다대다 관계는 관계형 데이터베이스는 물론이고 엔티
티에서도 거의 사용하지 않는다. 따라서 그림처럼 주문상품이라는 엔티티를 추가해서 다대다 관계를 일대
다, 다대일 관계로 정리한다.

**상품 분류:** 상품은 도서, 음반, 영화로 구분되는데 상품이라는 공통 속성을 사용하므로 상속 구조로 표현했
다.

### 테이블 설계
![db_table](https://user-images.githubusercontent.com/5433728/148404456-21b898e5-1895-4d3b-a287-a44bd1dde609.jpg)

**회원(MEMBER):** 회원테이블은 오더테이블과 일대다 관계를 가지고 있다. 오더테이이블에 MEMBER_ID(FK)로 연관관계를 표현한다.

**주문(ORDERS)과 상품(ITEM):** 주문과 상품 테이블은 다대다 연관을 가진다. 이경우 ORDER_ITEM 테이블을 두어 다대다 관계를 일대다와 다대일관계로
변환하여 관리한다.

**주문(ORDERS)과 배송(DELIVERY):** 주문과 배송 테이블은 일대일 관계로하여 일대일 관계에 대해 JPA에서 어떻게 관리하는지 확인한다.

**카테고리(CATEGORY)와 상품(ITEM):** 카테고리와 상품 테이블도 다대다 관계로 CATEGORY_ITEM 테이블을 두어 다대다 관계를 일대다, 다대일 관계로 정리한다.
### 엔티티 설계

![domain_entity](https://user-images.githubusercontent.com/5433728/148403925-b7394d15-2b50-4e5e-8d0d-9113c5a85142.jpg)
----
**회원(Member):** 이름과 임베디드 타입인 주소( Address ), 그리고 주문( orders ) 리스트를 가진다.
주문(Order): 한 번 주문시 여러 상품을 주문할 수 있으므로 주문과 주문상품( OrderItem )은 일대다 관계
다. 주문은 상품을 주문한 회원과 배송 정보, 주문 날짜, 주문 상태( status )를 가지고 있다. 주문 상태는 열
거형을 사용했는데 주문( ORDER ), 취소( CANCEL )을 표현할 수 있다.

**주문상품(OrderItem):** 주문한 상품 정보와 주문 금액( orderPrice ), 주문 수량( count ) 정보를 가지고
있다. (보통 OrderLine , LineItem 으로 많이 표현한다.)

**상품(Item):** 이름, 가격, 재고수량( stockQuantity )을 가지고 있다. 상품을 주문하면 재고수량이 줄어든
다. 상품의 종류로는 도서, 음반, 영화가 있는데 각각은 사용하는 속성이 조금씩 다르다.

**배송(Delivery):** 주문시 하나의 배송 정보를 생성한다. 주문과 배송은 일대일 관계다.

**카테고리(Category):** 상품과 다대다 관계를 맺는다. parent , child 로 부모, 자식 카테고리를 연결한
다.

**주소(Address):** 값 타입(임베디드 타입)이다. 회원과 배송(Delivery)에서 사용한다.

# 아래내용은 마크다운 사용법 참조용(세미나와 관련없음)

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


![springio_setting](https://user-images.githubusercontent.com/5433728/148364927-3e907ac5-577e-4ef1-be5c-3619814ee78b.jpg) "링크 설명(title)을 작성하세요.")

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
