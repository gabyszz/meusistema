#!/bin/bash
# Uso: sudo ./deploy.sh
set -e

TOMCAT=/opt/tomcat10
APP=meusistema

echo ">> Atualizando código..."
git pull origin main

echo ">> Compilando..."
mvn clean package -q

echo ">> Copiando configuração e .war..."
mkdir -p $TOMCAT/conf/Catalina/localhost
cp config/$APP.xml $TOMCAT/conf/Catalina/localhost/$APP.xml
cp target/$APP.war $TOMCAT/webapps/$APP.war

echo ">> Reiniciando Tomcat..."
systemctl restart tomcat10.service

echo ">> Pronto! Acesse http://IP_DA_VM:8080/$APP"