<!-- 

## 코드 리뷰 팁

- 코드와 관련된 질문이 있다면, PR 본문에 적기 보다는 해당 코드를 선택하고 코멘트를 남겨주세요.
  - [참고: Adding comments to a pull request](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/reviewing-changes-in-pull-requests/commenting-on-a-pull-request#adding-comments-to-a-pull-request)

-->

## 체크 리스트

- [x] 미션의 필수 요구사항을 모두 구현했나요?
- [x] Gradle `test`를 실행했을 때, 모든 테스트가 정상적으로 통과했나요?
- [x] 애플리케이션이 정상적으로 실행되나요?
- [x] [프롤로그](https://prolog.techcourse.co.kr)에 셀프 체크를 작성했나요?
  - [코기 프롤로그](https://prolog.techcourse.co.kr/studylogs/3929)

## 객체지향 생활체조 요구사항을 얼마나 잘 충족했다고 생각하시나요?

### 1~5점 중에서 선택해주세요.

- [ ] 1 (전혀 충족하지 못함)
- [ ] 2
- [x] 3 (보통)
- [ ] 4
- [ ] 5 (완벽하게 충족)

### 선택한 점수의 이유를 적어주세요.

<!-- 이유 작성 -->
```규칙 4: 한 줄에 점을 하나만 찍는다.```

해당 규칙을 지키지 못한 코드가 존재한다.
  
## 어떤 부분에 집중하여 리뷰해야 할까요?

<!-- 리뷰어가 효과적으로 피드백할 수 있도록 중점적으로 피드백받고 싶은 내용을 공유해주세요.  
예를 들어, 가장 고민했던 점이나 여전히 어려운 부분, 그리고 이에 대한 생각을 적을 수 있습니다. -->

코드에 대한 질문은 아니고 TDD에 대한 질문이 있습니다. 제가 TDD를 진행할 때 미리 도메인을 설계한 후에 테스트 작성을 하기 
시작했습니다. 그런데 TDD에 대해 강의를 들을 때 코치님은 최소한의 설계만 하고 바로 테스트 코드를 작성한다고 하셨습니다. 
TDD를 하면서 각 객체의 구성요소를 정하고 역할을 분배한다고 하셨습니다. 저는 여기서 최소한의 설계가 어느정도인지 감이 오지 않습니다.
이번에 TDD를 적용할 땐 미리 객체 구성요소를 설계 후 각 객체에 대해 TDD를 진행했는데 이렇게 하면 안되는건가요??
최소한의 설계가 어느 정도까지인지 궁금합니다!
