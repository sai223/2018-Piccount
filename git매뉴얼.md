# 2018-SW-Capstone-Design
Repository for Final Project

Git Manual
----------

#### 초기 세팅
> 1. local에서 폴더를 하나 정한다.
> 2. 해당 폴더에서 git bash 실행
> 3. git init
> 4. git remote add origin https://github.com/sai223/2018-Piccount
> 5. local 폴더의 .git 를 삭제하면 초기화된다.
*****
#### Git --> Local
> **git pull origin master** : git에 올라가 있는 프로젝트의 **현재 버전**으로 local을 바꾼다.
>> **git clone URL** 명령은 아래의 명령들을 하나로 묶은 것.
>> * git init
>> * git remote add origin URL
>> * git pull origin master
*****
#### Local --> Git
> 1. __git add *__ 또는 **git add 파일명/폴더명** : add 대상을 stage area에 올린다.
>> **git status** : 작업 도중에 현재까지 변경된 사항 등의 정보를 알 수 있다.
> 2. **git commit -m "commit message"** : stage area에 있는 내용을 local repository에 올린다.
>> commit message naming 규칙 : YYYYMMDD-이름-설명 (예시: 20171122-이인태-README.md 수정)
> 3. **git push origin master** : local repository에 있는 내용을 github repository에 올린다. 올리면 내용이 **덮어씌워**진다.
> 4. 용량 때문에 node_modules 폴더를 제외하고 올리는 것이 좋다. add할 때 node_modules 폴더만 제외하거나, 혹은 아래의 방법을 이용.
>> 1. local 폴더에서 **touch .gitignore** 실행하면 .gitignore라는 확장자가 없는 빈 파일이 생성된다.
>> 2. 해당 파일에 **node_modules/** 라고 적어두면 add할 때 자동으로 제외된다.

*****
*****
