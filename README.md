# 스프링 1주차 개인과제

## Use Case

![Group 9](https://user-images.githubusercontent.com/5901912/193529723-de2deca5-3eb2-4950-84d1-067fc1198c7b.png)

## API List

| Method | URL | Request | Response |
| --- | --- | --- | --- |
| GET | /api/post | - | {"success": true,"data": [{"createdAt": "2022-07-25T12:43:01.226062”,"modifiedAt": "2022-07-25T12:43:01.226062”,"id": 1,"title": "title2","content": "content2","author": "author2"},{"createdAt": "2022-07-25T12:43:01.226062”,"modifiedAt": "2022-07-25T12:43:01.226062”,"id": 2,"title": "title","content": "content","author": "author"}],"error”: null} |
| GET | /api/post/{id} | - | {"success": true,"data": {"createdAt": "2022-07-25T12:43:01.226062”,"modifiedAt": "2022-07-25T12:43:01.226062”,"id": 1,"title": "title2","content": "content2","author": "author2"},"error": null} |
| POST | /api/post | {"title" : "title","content" : "content","author" : "author","password" : "password"} | {"success": true,"data": {"createdAt": "2022-07-25T12:43:01.226062”,"modifiedAt": "2022-07-25T12:43:01.226062”,"id": 1,"title": "title","content": "content","author": "author"},"error": null} |
| POST | /api/post/{id} | {"password" :"password"} | {"success": true,"data": true,"error": null} |
| PUT | /api/post/{id} | {"title" : "title2","content" : "content2","author" : "author2","password" : "password2"} | {"success": true,"data": {"createdAt": "2022-07-25T12:43:01.226062”,"modifiedAt": "2022-07-25T12:43:01.226062”,"id": 1,"title": "title2","content": "content2","author": "author2"},"error": null} |
| DELETE | /api/post/{id} |  | {"success": true,"data": true,"error": null} |


## Git Actions

### 적용항목

1. Gradle Build
2. Make Docker image
3. AWS ECR Upload
4. AWS ECS Deploy

### Code
```yaml
name: Java CI/CD

# main브랜치 push / PR 시 실행
on:
  push:
     branches: [ "main" ]
  pull_request:
     branches: [ "main" ]
    
# 도커 이미지 태그 지정
env:
  IMAGE_TAG: latest

permissions:
  contents: read

jobs:
  build:
    # 우분투 세팅
    runs-on: ubuntu-latest
    # JDK 11 세팅
    steps:
    - uses: actions/checkout@v3
    - name: Set up JDK 11
      uses: actions/setup-java@v3
      with:
        java-version: '11'
        distribution: 'temurin'
        
    # Gradle 빌드 시작
    - name: Build with Gradle
      uses: gradle/gradle-build-action@67421db6bd0bf253fb4bd25b31ebb98943c375e1
      with:
        arguments: build
    
    # AWS 인증 설정 / secret에 id, secret, region 등록
    - name: Configure AWS credentials
      uses: aws-actions/configure-aws-credentials@v1
      with:
        aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
        aws-secret-access-key: ${{ secrets.AWS_ACCESS_KEY_SECRET }}
        aws-region: ${{ secrets.AWS_REGION }}
    # ECR 로그인
    - name: Login to Amazon ECR
      id: login-ecr
      uses: aws-actions/amazon-ecr-login@v1
    # 얻로드할 도커 이미지 제작 및 푸쉬
    - name: Build, tag, and push image to Amazon ECR
      id: build-image
      env:
        ECR_REGISTRY: ${{ steps.login-ecr.outputs.registry }}
        IMAGE_TAG: ${{ github.sha }}
      run: |
        docker build -t ${{ secrets.ECR_REGISTRY }}/${{ secrets.ECR_REPOSITORY }}:$IMAGE_TAG .
        docker push ${{ secrets.ECR_REGISTRY }}/${{ secrets.ECR_REPOSITORY }}:$IMAGE_TAG
        echo "::set-output name=image::${{ secrets.ECR_REGISTRY }}/${{ secrets.ECR_REPOSITORY }}:$IMAGE_TAG"
    # 업로드된 ECR 이미지 ID값을 ECS 설정값에 추가
    - name: Fill in the new image ID in the Amazon ECS task definition
      id: task-def
      uses: aws-actions/amazon-ecs-render-task-definition@v1
      with:
        task-definition: task-definition.json
        container-name: spring_week1
        image: ${{ steps.build-image.outputs.image }}
    # ECS에 이미지 배포
    - name: Deploy Amazon ECS task definition
      uses: aws-actions/amazon-ecs-deploy-task-definition@v1
      with:
        # task-definition: task-definition.json
        task-definition: ${{ steps.task-def.outputs.task-definition }}
        service: hanghae99-puri12
        cluster: hanghae99-puri12
        wait-for-service-stability: true
```

### 작동화면

#### ECR 콘솔
![image](https://user-images.githubusercontent.com/5901912/193756613-1810ee31-77ea-4e83-bea6-b43792a60bba.png)

#### ECS 콘솔
![image](https://user-images.githubusercontent.com/5901912/193756758-16c8ee77-fa7c-4ede-bff3-c74f2f36b4e3.png)

#### Postman 실행결과
![image](https://user-images.githubusercontent.com/5901912/193756893-723b042f-5c49-4a05-9cb9-22daf592b9b0.png)

