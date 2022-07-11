#Shortly

#About
URL shortening is a technique on the Web in which a Uniform Resource Locator (URL) may be made substantially shorter and still direct to the required page. This is achieved by using a redirect that links to the web page that has a long URL.

# Requirements to run the code:

**Pre-Requisite to running & installations**

- Java development kit(JDK) 11
- Android Studio Chipmunk | 2021.2.1 Patch 1
- Android gradle plugin version: 7.3.3
- Min API Level: 21
- Android Gradle Plugin version: 7.2.1
- TargetSdkVersion: 32

# Technologies stack(Features):

- Android MVVM (ModelView-View-Model) design pattern.
- Reactive Programming.
- Programing Language: Kotlin.
- Jetpack Navigation.
- Material them Day(Light support)
- Network library: Retrofit + logging Interceptors.
- Kotlin Coroutines & live data
- Android View Binding
- Hilt for dependency Injection.
- Leak canary added for memory leaks detection.
- Spotless for linting. A wrapper to ktlint.
- Pre commit hook for lint check & auto fix(i.e unused imports, code format).
- Work with development branch, lets raise the PR from development to main branch. in multi-team members, we can use the feature branch or forked repository mechanism.
- Junit4 + Mockito for unit testing.

#Known Exemption

- Dark theme is not supported due to unavailability of the drawable resources.
- Orientation is restricted to portrait as given design is not friendly for landscape.
- I'm really sorry due to time constraints, I'm unable to write the test. But I am sure that I have written a testable code. We can have a discussion about test cases. 

# Why I chose

**MVVM Architecture**
As google has made MVVM official Android architecture. It resolve lot of legacy issues like
- Configuration changes data Persistence with viewModel
- Supports layers
- No View coupling with business logic
- Avoid memory leaks with lifeCycle awareness.
- Easier to maintain the code.
- Write testable code.
- And many more.

**Spotless Linter & Pre commit hook**
- It's always a challenge to maintain the code. Team members some time forgot to remove unused imports or code formats. More then that we can have custom rules to apply on code. i.e file column range, run tests before pushing code. This helps to identify the issue at earlier stages

**Leak Canary**
It really helps to identify the memory leaks at development level. Its installed with debugImplementation and no size impact on production releases.

**Room over SQLite**
Room is recent google official library to persist data. Its actually a wrapper to SQLite. I chose it as the database requirements was straightforward

**Dependencies organisation**
I have organized all dependencies in dependencies.gradle file. By keeping in mind the scalability factor, It helps in multi-module project.

**Dependency Injection**
This is one of SOLID programming principle and it really help to organize the dependencies. Dependency injection helps in writing a testable code.

**Retrofit Networking Library**
Retrofit is the most commonly used library for network call in Android ecosystem. It has a good update version history and support the coroutines & MVVM.

**Jetpack Navigation**
It was always hard to make custom fragment transaction. New navigationAPI's are easy to use and navigation, deep links, passing argument made very easy.

**Launcher Icon - Had fun**
As i was instructed to assume build is were going to production. I have added a custom launcher icon,


# Reference Resources
- Working APK (https://drive.google.com/file/d/1B_XU70N_9QvZ-BUbuDUZloUTSmJ_uibg/view?usp=sharing)
- Video demonstration(https://drive.google.com/file/d/1Tukn6SvKbDr9sm72dx2Q6yPmrM-Dbc6T/view?usp=sharing)


