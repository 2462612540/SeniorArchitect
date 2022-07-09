#!/usr/bin/env sh

# 确保脚本抛出遇到的错误
set -e

# 生成静态文件
npm run docs:build

# 进入生成的文件夹
# cp CNAME docs/.vuepress/dist/
cd docs/.vuepress/dist


git init
git add -A
git commit -m 'feat(seniorarchitecture) [xjl0073] init repo and creat blog'

# 如果发布到 https://<USERNAME>.github.io/<REPO>
git push -f https://2462612540.github.io/SeniorArchitect.git master:blog


cd -

git init
git add -A
git commit -m 'feat(seniorarchitecture) [xjl0073] init repo and creat blog'
git push -f https://gitee.com/xjl2462612540/SeniorArchitect.git master:master
git push -f https://github.com/2462612540/SeniorArchitect.git master:master