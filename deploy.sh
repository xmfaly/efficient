#!/bin/bash


#docker 用户名
USER_NAME=xmfaly
# 项目名称
PROJ_NAME=efficent
# 服务器用户名
SERVER_NAME=root
# 服务器地址
SERVER_HOST=120.78.149.248
# 部署端口
DEPLOY_PORT=8082

# 停止原来的容器
# docker stop $PROJ_NAME

# 删除原来的容器和镜像
docker rm $PROJ_NAME
docker rmi $USER_NAME/$PROJ_NAME

#拉取更新
git pull

# 本地构件镜像
./mvnw package -Dmaven.test.skip=true docker:build

# 上传镜像到远程仓库
docker push $USER_NAME/$PROJ_NAME

# 连接服务器
ssh $SERVER_NAME@$SERVER_HOST -tt << cmd

# 停止原来的容器
docker stop $PROJ_NAME

# 删除原来的容器和镜像
docker rm $PROJ_NAME
docker rmi $USER_NAME/$PROJ_NAME

# 拉取最新的镜像
docker pull $USER_NAME/$PROJ_NAME

# 运行容器
docker run -d --name=$PROJ_NAME -p $DEPLOY_PORT:8080 -v /root/log/:/log/ -t $USER_NAME/$PROJ_NAME

cmd
