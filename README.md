# spring-vault
spring vault demo

## 1. start vault
src/test/bash/local\_run\_vault.sh

## 2. initial env
source src/test/bash/env.sh

## 3. initial example
src/test/bash/setup\_examples.sh

## 4. add read mysql policy
path "mysql/*" {
  capabilities = ["create", "read", "update", "delete", "list"]
}

path "sys/*" {
  capabilities = ["create", "read", "update", "delete", "list"]
}

path "secret/*" {
  capabilities = ["create", "read", "update", "delete", "list"]
}

## 5.set appid
vault auth-enable app-id  
vault write auth/app-id/map/app-id/my-spring-boot-app value=read-secret,read-mysql display_name=spring-boot-app  
vault write auth/app-id/map/user-id/{appid} value=my-spring-boot-app

### 5.1 IP=127.0.1.1 from spring cloud vault
src/test/bash/setup_appid.sh 32BCD336456E1B8C10DE511403655B6DE35390DA834B7C65E9F92E92364D6D
### 5.2 IP=127.0.1.1 from echo -n 127.0.1.1 | sha256sum
src/test/bash/setup_appid.sh 32bcd336456e1b8c10de51014036055b6de35390da834b7c65e9f92e92364d6d