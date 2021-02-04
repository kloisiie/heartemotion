const fs = require('fs');
const path = require('path');
let mark = process.argv[2];
let newName = process.argv[3];
console.log('newname', newName);
console.log('mark', mark);
let app = require('./app.json');
app.name = newName;
app.displayName = newName;
fs.writeFile('./app.json', JSON.stringify(app), 'utf8', () => {});
let pa = require('./package.json');
pa.name = newName;
fs.writeFile('./package.json', JSON.stringify(pa), 'utf8', () => {});

let imlPath = './android/Base.iml';
let newImlPath = './android/' + newName + '.iml';
fs.rename(imlPath, newImlPath, () => {});

let nameDir = './android/app/src/main/java/com/base/appname';
let newNameDir = './android/app/src/main/java/com/base/' + newName;
fs.rename(nameDir, newNameDir, () => {});

let markDir = './android/app/src/main/java/com/base';
let newMarkDir = './android/app/src/main/java/com/' + mark;
fs.rename(markDir, newMarkDir, () => {});

function fileDisplay(filePath) {
  //根据文件路径读取文件，返回文件列表
  fs.readdir(filePath, function (err, files) {
    if (err) {
      console.warn(err);
    } else {
      //遍历读取到的文件列表
      files.forEach(function (filename) {
        //获取当前文件的绝对路径
        var filedir = path.join(filePath, filename);
        //根据文件路径获取文件信息，返回一个fs.Stats对象
        fs.stat(filedir, function (eror, stats) {
          if (eror) {
            console.warn('获取文件stats失败');
          } else {
            var isFile = stats.isFile(); //是文件
            var isDir = stats.isDirectory(); //是文件夹
            if (isFile) {
              console.log(filedir);
            }
            if (isDir) {
              fileDisplay(filedir); //递归，如果是文件夹，就继续遍历该文件夹下面的文件
            }
          }
        });
      });
    }
  });
}
