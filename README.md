#스프링부트와 JPA 스터디

##준비사항
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
다운로드 받은 zip 파일을 해제하고 IDE로 임포트하면 그래들 프로젝트가 생성됩니다. 
  * 


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
##DomoApplication 동작확인

앱어플리케이션 실행후 8080포트로 어플리케이션 기동된다. 부트는 최초 기동시 @SpringBootApplication 어노테이션이 있는 
파일을 확인하여 기동한다. 프로젝트를 생성하게 되면 자동생성되는 파일로 org.springframework.boot:spring-boot-starter-web을 
디펜던시로 추가하게 되면 톰캣을 기본으로 내장하여 기동하게 된다. 

[그림 2] IDE에 프로젝트 임포트 
![graddle_project](https://user-images.githubusercontent.com/5433728/148372869-562d1d05-6f23-4152-81ce-a665d6c02cf3.jpg)

부트기동 시 시작지점

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
##스프링 부트 thymeleaf viewName 매핑

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

##H2 DB 설치
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
3. 테스트
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
[그림 7 테스트성공]

![jap_db_integration_success](https://user-images.githubusercontent.com/5433728/148391704-96ba70a9-ece0-490b-a2a0-19ac8958e01a.jpg)


지금까지가 스프링부트환경에서 JPA demo를 개발하기 위한 환경 구성이다.

앞으로는 고객의 요구사항을 분석하여 도메인을 설계하고 도메인을 바탕으로 JPA이용하여 기능을 구헌하면서 JPA 구조와
사용방법을 알아보도록 하겠다.



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
