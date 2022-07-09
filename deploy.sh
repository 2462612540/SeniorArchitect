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
git commit -m 'feat(seniorarchitecture) [xjl000] init blog '

# 如果发布到 https://<USERNAME>.github.io/<REPO>
 git push -f git@github.com:2462612540/SeniorArchitect.git master:blog


cd -

git init
git add -A
git commit -m 'feat(seniorarchitecture) [xjl000] init blog '

git push -f git@github.com:2462612540/SeniorArchitect.git master:master