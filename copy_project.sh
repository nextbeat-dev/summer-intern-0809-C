#! /bin/bash
input1=$1
input2=$2
# inputをすべて小文字にする
snake1=`echo ${input1} | tr 'A-Z' 'a-z'`
snake2=`echo ${input2} | tr 'A-Z' 'a-z'`
# snakeがai_scoreの時、AiScoreにする
pascal1=`echo $snake1 | gsed -r 's/(^|_)(.)/\U\2\E/g'`
pascal2=`echo $snake2 | gsed -r 's/(^|_)(.)/\U\2\E/g'`

mkdir -p app/controllers/${snake2}
cp -i app/controllers/${snake1}/${pascal1}Controller.scala app/controllers/${snake2}/${pascal2}Controller.scala
gsed -i "s/$pascal1/$pascal2/g" app/controllers/${snake2}/${pascal2}Controller.scala
gsed -i "s/$snake1/$snake2/g" app/controllers/${snake2}/${pascal2}Controller.scala


cp -i app/model/site/app/${pascal1}.scala app/model/site/app/${pascal2}.scala
gsed -i "s/$pascal1/$pascal2/g" app/model/site/app/${pascal2}.scala
gsed -i "s/$snake1/$snake2/g" app/model/site/app/${pascal2}.scala


mkdir -p app/persistence/${snake2}/{dao,model}
cp -i app/persistence/${snake1}/dao/${pascal1}Dao.scala app/persistence/${snake2}/dao/${pascal2}Dao.scala
gsed -i "s/$pascal1/$pascal2/g" app/persistence/${snake2}/dao/${pascal2}Dao.scala
gsed -i "s/$snake1/$snake2/g" app/persistence/${snake2}/dao/${pascal2}Dao.scala
cp -i app/persistence/${snake1}/model/${pascal1}.scala app/persistence/${snake2}/model/${pascal2}.scala
gsed -i "s/$pascal1/$pascal2/g" app/persistence/${snake2}/model/${pascal2}.scala
gsed -i "s/$snake1/$snake2/g"   app/persistence/${snake2}/model/${pascal2}.scala


mkdir -p app/views/site/${snake2}/
cp -ir app/views/site/${snake1}/ app/views/site/${snake2}/
gsed -i "s/$pascal1/$pascal2/g" app/views/site/${snake2}/*.scala.html
gsed -i "s/$snake1/$snake2/g"   app/views/site/${snake2}/*.scala.html



