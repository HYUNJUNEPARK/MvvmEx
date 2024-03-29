Architecture
===================================
안드로이드 아키텍처의 기본 클래스/구성요소</br>
-**(1)UI Controller(Activity/Fragment)** : Display data and capture user event</br>
화면에 뷰를 그리고 사용자 이벤트나 사용자가 상호작용하는 다른 모든 UI 관련 동작을 캡쳐하여 UI를 제어</br>
앱의 데이터 또는 데이터에 관한 모든 의사 결정 로직은 UI Controller 클래스에 포함되어서는 안됨</br>
안드로이드 시스템은 메모리 부족과 같은 시스템 조건으로 인해 언제든지 UI Controller 를 제거할 수 있음</br>
->이러한 이벤트는 개발자가 직접 제어할 수 없기 때문에, UI Controller 에 앱 데이터나 상태를 저장해서는 안됨</br>


-**(2)ViewModel** : Holds all the data needed for the UI and prepares it for display</br>
뷰에 표시되는 앱 데이터의 모델</br>
안드로이드 프레임워크에서 활동이나 프래그먼트가 소멸되고 다시 생성될 때 폐기되지 않는 앱 관련 데이터를 저장</br>
뷰모델은 액티비티나 프래그먼트 인스턴스처럼 소멸되지 않고 구성이 변경되는 동안 자동으로 유지되어 보유하고 있는 데이터가</br>
다음 액티비티나 프래그먼트 인스턴스에 즉시 사용될 수 있음</br>


-**(3)LiveData**</br>
수명 주기를 인식하는 관찰 가능한 데이터 홀더 클래스</br>
모든 유형의 데이터에 사용할 수 있고 데이터를 보유할 수 있는 래퍼</br>
LiveData 객체에서 보유한 데이터가 변경되면 관찰자에 알림이 제공됨</br>
LiveData 에 관찰자를 연결하면 관찰자는 LifecycleOwner(일반적으로 액티비티나 프래그먼트)와 연결됨</br>


-**(4)Room**</br>


이러한 구성요소는 수명 주기의 복잡성을 어느 정도 처리하므로 수명 주기 관련 문제를 피하는 데 도움을 줌


ViewModel 추가하기
===================================
(1)의존성 추가
```kotlin
// ViewModel
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1'
```

(2)뷰모델 클래스 생성
```kotlin
class GameViewModel : ViewModel() {
}
```

(3)ViewModel 를 프래그먼트에 연결하기
```kotlin
private val viewModel: GameViewModel by viewModels()
```

LiveData 추가하기
===================================

`private val _currentScrambledWord = MutableLiveData<String>()`</br>
-MutableLiveData : LiveData의 변경 가능한 버전</br>
-LiveData/MutableLiveData 객체의 값은 동일하게 유지되고 이 객체에 저장된 데이터만 변경되기 때문에 변수 유형은 val 로 선언</br>
-LiveData 객체 내의 데이터에 엑세스하려면 `value` 속성을 사용</br>
`_currentScrambledWord.value = String(tempWord)`</br>


LiveData 객체에 관찰자 연결하기
===================================
```kotlin
// Observe the scrambledCharArray LiveData, passing in the LifecycleOwner and the observer.
viewModel.currentScrambledWord.observe(
   viewLifecycleOwner,
   { newWord ->
       binding.textViewUnscrambledWord.text = newWord
   })

viewModel.currentScrambledWord.observe(viewLifecycleOwner) { newWord ->
    binding.textViewUnscrambledWord.text = newWord
}
```


dataBinding 사용하기
===================================
```kotlin
//build.gradle(Module)
buildFeatures {
   dataBinding = true
}

//...

plugins {
    //id 'com.android.application'
    //id 'kotlin-android'
    //id 'kotlin-kapt'
}

//binding 변수의 인스턴스화
binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
```

참고 링크
---------------
프로젝트 출처 : https://developer.android.com/codelabs/basic-android-kotlin-training-viewmodel?hl=ko#0
