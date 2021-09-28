## kotlin-coroutine-basis

## [Coroutine basics](https://kotlinlang.org/docs/coroutines-basics.html)
### Your First Coroutine
* runBlocking {}
  * 코루틴 빌더로 선언했을 시 내부 블럭은 코루틴 스코프가 생긴다.
  * 현재 컨텍스트 내 실행되는 스레드는 내부의 코루틴을 수행이 완료될 때가지 block 된다.
    * 응용 프로그램 최상위에서 runBlocking 이 사용되는 것을 확인할 수 있다.
* launch {}
  * 코루틴 빌더이고 새로운 코루틴 스코프의 시작이다.
* delay()
  * 코루틴 스코프 내에서 일시중지할 수 있는 특별한 함수
* structure concurrency ⭐⭐⭐
  * structure concurrency 란 코루틴 스코프 내에서 또 다른 코루틴 스코프를 만들 수 있는 것을 의미한다.

### Extract function refactoring
* suspend keyword
  * 코루틴 스코프 내에 있는 코드로직을 별도의 함수로 추출한다. 그리고 해당 함수에 suspend 키워드를 붙일 수 있다.
  
### Scope Builder
* 코루틴 스코프는 다른 빌더를 제공하고 있다. coroutineScope 빌더를 사용해서 자신만의 빌더를 선언할 수 있다. 실행된 자식이 모두 실행될 때까지 완료되지 않는다.
* runBlocking 과 coroutineScope 는 자식이 끝날 때까지 기다리는 부분이 비슷하게 보인다. 근데 가장 큰 차이점이 있다.
  * runBlocking 은 current thread 를 block 한다. current thread 가 waiting 하기 위해서.
  * coroutineScope 는 단지 suspend (일시중지) 한다. 그리고 기본 스레드는 waiting 하지 않는다.
  * 이러한 차이점으로 runBlocking 은 `일반함수` 이고 `coroutineScope` 는 `일시정지 함수` 이다.

### Scope Builder And Concurrency
* coroutineScope 빌더는 여러 작업을 동시에 처리하기 위해 suspend function 을 이용할 수 있다.

### An explicit job
* launch {} 코루틴 빌더는 job 을 리턴한다. 
* job 은 실행된 코루틴이고 완료할 때까지 기다릴 수 있다.
* 예를들어 자식 코루틴이 완료될 때까지 기다릴 수 있다.

## [Cancelling coroutine execution](https://kotlinlang.org/docs/cancellation-and-timeouts.html)
* 백그라운드로 돌아가는 coroutine 에 대해서 세밀한 컨트롤이 필요할 때가 있다.

### Cancellation is cooperative
* coroutine cancellation 은 cooperative 하다고 한다. (취소에 협조적이라고 함)
* kotlinx.coroutines 에 있는 일시정지 함수 (suspend function) 은 취소가 가능하다.
  * `suspend` function 은 coroutine 을 취소시키기 위해서 필요하다. coroutine scope 내에 `suspend` function 이 있어야 한다.
* 코루틴은 취소가 되는 경우에 `CancellationException` 을 throw 한다. 근데 코루틴 로직이 실행중이고 `Cancellation` 을 체크하지 않은 경우에 취소되지 않는다.
* 코루틴을 취소가능하게 하기
  * yield() 를 넣어준다. yield() 는 suspend function 인데 코루틴을 취소가능토록 해준다.
  * 명시적으로 상태를 체크하도록 한다. coroutine scope 내에 `isActive` 를 넣어준다.

### Closing resources with finally 
* finally {} 에서 resources 를 closing 할 수 있다.

### Run non-cancellable block
* finally {} 에서 별도의 취소가 불가능한 코루틴 블럭을 만들 수 있다. `withContext(NonCancellable)`

### Timeout
* 코루틴 실행을 취소하는 현실적인 방법은 제한시간이 초과되었을 때 취소하는 방법이다.

## [Composing suspending functions](https://kotlinlang.org/docs/composing-suspending-functions.html)
* suspending functions 을 구성하는 여러방식들을 확인한다.

### Sequential by default
* 순차적인 함수 호출을 통해서도 코루틴을 수행할 수 있다.

### Concurrent using async
* 순차적인 함수 호출이 아닌 동시에 수행하여 좀 더 빠르게 하고자 한다면 `async` 를 이용하여야 한다.
* `async` 는 개념적으로 `launch` 와 유사하다. 경량화된 스레드, 코루틴이 분리되는데 다른 코루틴과 함께 동시에 실행된다.
* 차이점으로는 `launch 는 job 을 반환한다. 그리고 결과값을 가지고 있지 않는다.` `async 는 deferred` 를 반환한다.
  * async deferred 는 결과값을 추후에 제공할 수 있다.
  * deferred 도 결국 job 인데 필요에 따라서 취소가 가능하다.

### Lazily started async
* async 를 조금은 느리게 실행하는 방법
* `async(start = CoroutineStart.LAZY)` 를 작성하게 되면 비동기 코루틴을 조금 느리게 실행할 수 있다. deferred 는 이후에 start() 를 해주어야 한다.
* deferred.start() 가 없이 await() 만 설정하면 비동기 코루틴은 블락되어 순차적으로 수행된다.

### Async-style functions
* 코틀린에서 하지 말라는 형태
  * async block 을 코루틴 블럭 외부에서 쓰지 말기. 별도의 함수로 추출해서 쓰지 말기. 그렇게 쓰면 익셉션 발생 시, 서브 코루틴들은 중간에 종료되지 못한다.
* GlobalScope launch 하는 형태로 함수를 감싸서 사용하지 말기.
  * suspend 형태로 코루틴을 조합해서 쓰는 것이 좋다.

### coroutine under the hood
> 코루틴은 마법이 아니다. 일반 코드처럼 실행된다.
* state machine (sm)
  * 현재의 호출 상태를 의미한다. `몇 번째 label 로 가야하는지` 정보도 여기 담긴다.

## TIP
* 코루틴 스코프 내의 코루틴 이름을 알고 싶다면 intellij vm option 을 `-Dkotlinx.coroutines.debug` 로 준다
  * `println(Thread.currentThread.name())` 으로 작성한다

## reference
* https://kotlinlang.org/docs/coroutines-overview.html
* [인프런 코루틴 무료 강의](https://www.inflearn.com/course/%EC%83%88%EC%B0%A8%EC%9B%90-%EC%BD%94%ED%8B%80%EB%A6%B0-%EC%BD%94%EB%A3%A8%ED%8B%B4/dashboard)
* https://proandroiddev.com/structured-concurrency-in-action-97c749a8f755