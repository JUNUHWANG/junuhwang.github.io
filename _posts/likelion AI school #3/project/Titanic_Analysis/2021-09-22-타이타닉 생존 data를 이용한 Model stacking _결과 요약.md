---
title: "타이타닉 생존 data를 이용한 Model stacking _결과 요약"
categories:
  - python
  - likelion
  - project
  - Data analysis
  - Modeling
tags:
  - python
  - likelion
  - ML
  - Data analysis
  - Modeling
  - Titanic data
---

## Project Process 별 자료 (총 3편)

<a href="https://junuhwang.github.io/python/likelion/project/타이타닉 생존 data를 이용한 Model stacking _결과 요약"> 타이타닉 생존 data를 이용한 Model stacking _결과 요약</a>  

<a href="https://junuhwang.github.io/python/likelion/project/타이타닉 생존 data를 이용한 Model stacking (1)_데이터 전처리편"> 타이타닉 생존 data를 이용한 Model stacking (1)_데이터 전처리편 </a>  

<a href="https://junuhwang.github.io/python/likelion/project/타이타닉 생존 data를 이용한 Model stacking (2)_모델링 편"> 타이타닉 생존 data를 이용한 Model stacking (2)_데이터 모델링편 </a>  


## 다룰내용
- Titanic Data를 이용한 데이터 전처리 및 ML 적용

## 프로젝트 진행 기간
- 2021-07-01(목) ~ 2021-07-05(월)

## 이용 데이터
- Titanic 생존 Data
 
## 전처리 과정
- 객실 등급 간 티켓 비용의 차이가 있어 해당 내용에 대해 보정 진행
  * 확인 도중 동일 티켓번호에 있었고, 동일 티켓번호 보유 인원 수 만큼 비용을 나눔
- 단체(가족, 지인)여부 확인 후 추가
  * 동일 티켓 번호 및 동승 가족 수 data를 확인하여 분류 진행
- 결측치 데이터 추정 후 업데이트
  * 나이 : 이름 내 호칭을 분류하여 ML 분석 진행 후 적절한 모델 선정, 추가함
  * 승무원 여부 및 탑승 항구는 생존에 영향을 미치지 않을 것이라 판단, 해당 Data 삭제 후 분석 진행

## 사용 라이브러리
- pandas : 데이터 불러오기 및 내보내기, 데이터 프레임 자료 전처리에 사용
- numpy : 분석 중 일부 변수에 대해서는 np.array로 변환 필요
- sklearn : 각종 ML 분석을 위한 모델과 평가를 하기위한 함수 다수 보유
- lightgbm : sklearn 내에 해당 모델은 없어 추가적으로 필요
- matplotlib : 최종 ROC Curve 그래프 시각화

## 결론
- Random Forest 모델이 해당 데이터에서는 가장 적합한 것으로 확인됨 (AUC : 0.781)

## 느낀점 및 한계
- 다른 조의 발표를 들어본 결과 가장 높은 조의 AUC가 0.80 정도 나온 것으로 확인
- 데이터 전처리를 어떻게 진행하느냐에 따라서 적합한 모델 및 AUC 값이 달라지는 것을 확인 
- 별도 Scaling 작업 진행 없이 분석을 진행하였으나 오히려 AUC가 잘 나온 조도 있었음  
  이를 통하여 Data scaling 작업을 진행하는 것이 항상 좋은 결과가 나온다는 것이 아님을 깨달음
- 회귀 분석 진행 시에는 영향력이 적은 변수를 제거하면 R-square 값이 증가하는 것을 경험하였으나, ML에서는 AUC 및 Accuracy Score가 크게 변화하는 모습을 보이지 않음
- Gradient Boosting 등 여러 모델에 Grid Search를 진행하고자 하였으나 CPU의 한계로 다운되는 현상 발생  
  Colab 사용 시에도 중간에 다운 및 일일 무료 사용 용량 초과가 되는 현상 발생
- 팀 프로젝트 기간 중 주말 제외한 3일 중 2일을 데이터 전처리에 사용함
- 최종 모델 구현하기 전 데이터 전처리 작업이 중요하고 많은 시간이 소요되는 것을 깨달음
- 시간이 부족하여 팀 내 논의했던 다양한 방법을 시도하지 못하여 아쉬움이 남았음
