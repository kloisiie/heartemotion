const fs = require('fs');
const path = require('path');
let dir = process.argv[2];
//用来处理图片引用的nodejs文件
function fileDisplay(filePath) {
  //根据文件路径读取文件，返回文件列表
  fs.readdir(filePath, function (err, files) {
    if (err) {
      console.warn(err);
    } else {
      //遍历读取到的文件列表
      let str = '';
      files.forEach(function (filename) {
        if (filename.indexOf('@') != -1) {
        } else {
          let name = filename.replace('.png', '');
          str = str + `\n${name}:require('./images/${filename}'),`;
        }
      });
      console.log(str);
    }
  });
}

fileDisplay(dir);

/**
 * 使用方式: node imageHandler  [文件路径]
 * 将log打印的js复制到imgeRes.js文件即可
 */
