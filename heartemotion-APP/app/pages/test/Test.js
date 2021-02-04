import React, {PureComponent} from 'react';
import {
  StyleSheet,
  Text,
  View,
  Image,
  TouchableWithoutFeedback,
  FlatList,
} from 'react-native';
import {SafeAreaView} from 'react-native-safe-area-context';
import imageRes from '../../res/imgeRes';
import TestItem from './components/TestItem';
import RNFileSelector from 'react-native-file-selector';
import NetService from '../../service/NetService';
import {readFile} from 'react-native-fs';
export default class Test extends PureComponent {
  state = {data: [], selectArr: [], isAll: false};
  render() {
    return (
      <SafeAreaView style={styles.container} edges={['top']}>
        <View
          style={{
            flexDirection: 'row',
            alignItems: 'center',
            marginTop: 33,
            paddingHorizontal: 16,
          }}>
          <Text
            style={{fontSize: 24, color: '#333', fontWeight: 'bold', flex: 1}}>
            数据调试
          </Text>
          <TouchableWithoutFeedback onPress={this._onImport}>
            <View style={{flexDirection: 'row', alignItems: 'center'}}>
              <Image source={imageRes.icon_management_16} />
              <Text style={{fontSize: 16, color: '#333', marginLeft: 4}}>
                导入
              </Text>
            </View>
          </TouchableWithoutFeedback>

          <TouchableWithoutFeedback onPress={this._onSetting}>
            <View
              style={{
                flexDirection: 'row',
                alignItems: 'center',
                marginLeft: 28,
              }}>
              <Image source={imageRes.icon_set_20} />
            </View>
          </TouchableWithoutFeedback>
        </View>
        <FlatList
          style={{marginTop: 43}}
          data={this.state.data}
          renderItem={this._renderItem}
        />
        <View
          style={{
            height: 50,
            backgroundColor: 'white',
            flexDirection: 'row',
            alignItems: 'center',
            paddingHorizontal: 16,
          }}>
          <TouchableWithoutFeedback onPress={this._onAll}>
            <View style={{flexDirection: 'row', alignItems: 'center'}}>
              <Image
                source={
                  this.state.isAll
                    ? imageRes.icon_select_22_pre
                    : imageRes.icon_select_22_nor
                }
              />
              <Text style={{marginLeft: 4, fontSize: 14, color: '#666'}}>
                全选
              </Text>
            </View>
          </TouchableWithoutFeedback>
          <View style={{flex: 1}} />
          <TouchableWithoutFeedback onPress={this._onDelete}>
            <View
              style={{
                width: 83,
                height: 34,
                borderRadius: 17,
                borderWidth: 1,
                borderColor: '#FF4E56',
                justifyContent: 'center',
                alignItems: 'center',
                marginRight: 12,
              }}>
              <Text
                style={{fontSize: 15, color: '#FF4E56', fontWeight: 'bold'}}>
                删除
              </Text>
            </View>
          </TouchableWithoutFeedback>
          <TouchableWithoutFeedback onPress={this._onRun}>
            <View
              style={{
                width: 124,
                height: 34,
                borderRadius: 17,
                backgroundColor: '#5A60E9',
                justifyContent: 'center',
                alignItems: 'center',
              }}>
              <Text style={{fontSize: 15, color: '#fff', fontWeight: 'bold'}}>
                选择执行算法
              </Text>
            </View>
          </TouchableWithoutFeedback>
        </View>
      </SafeAreaView>
    );
  }
  componentDidMount() {
    this._unsubscribe = this.props.navigation.addListener('focus', async () => {
      this._loadList();
    });
  }
  componentWillUnmount() {
    this._unsubscribe && this._unsubscribe();
  }
  _loadList = async () => {
    let {res, error} = await NetService.debugList();
    if (!error) {
      this.setState({data: res.list});
    } else {
      this.setState({data: []});
    }
  };

  _onImport = () => {
    GBNavgator.navigate('ChooseFileType', {
      onChoose: (index) => {
        if (index == 2) {
          GBNavgator.navigate('ImportData');
        } else {
          this._chooseLocalFile();
        }
      },
    });
  };

  _chooseLocalFile = () => {
    RNFileSelector.Show({
      title: '选择文件',
      onDone: async (path) => {
        console.log('file selected: ' + path);
        if (!path || path.length == 0) {
          return;
        }
        try {
          let arr = path.split('/');
          let name = arr[arr.length - 1];
          console.log('文件名 ', name);
          let result = await readFile(path, 'utf8');
          console.log('读取文件 ', result);
          if (result && result.length > 0) {
            let {res, error} = await NetService.addLocalFile(result, name);
            if (!error) {
              this._loadList();
            }
          } else {
            GBPrompt.showToast('读取文件失败');
          }
        } catch (e) {
          console.log('读取文件失败 ', e);
          GBPrompt.showToast('读取文件失败');
        }
      },
      onCancel: () => {
        console.log('cancelled');
      },
    });
  };
  _onSetting = () => {
    GBNavgator.navigate('HostSetting');
  };
  _onItem = (item) => {
    let {data, selectArr, isAll} = this.state;
    let arr = [];
    if (selectArr.indexOf(item.id) != -1) {
      arr = selectArr.filter((value) => value != item.id);
    } else {
      arr = [...selectArr, item.id];
    }
    isAll = arr.length == data.length;
    this.setState({selectArr: arr, isAll});
  };
  _onDelete = async () => {
    let {selectArr} = this.state;
    if (selectArr.length == 0) {
      GBPrompt.showToast('未选择文件');
      return;
    }
    let {res, error} = await NetService.deleteDebugFile(selectArr.join(','));
    if (!error) {
      this.setState({selectArr: [], isAll: false});
      this._loadList();
    }
  };
  _onRun = () => {
    let {selectArr} = this.state;
    if (selectArr.length == 0) {
      GBPrompt.showToast('请选择调试文件');
      return;
    }

    GBNavgator.navigate('ChooseAlgorithm', {
      onSure: async (data) => {
        let {res, error} = await NetService.arithmeticExecute(
          data.id,
          selectArr,
        );
        if (!error) {
          this.setState({selectArr: [], isAll: false});
          GBNavgator.navigate('TestResult', {taskId: res.taskId});
        }
      },
    });
  };
  _onAll = () => {
    let {data, selectArr, isAll} = this.state;
    isAll = !isAll;
    if (isAll) {
      let arr = [];
      for (let i = 0; i < data.length; i++) {
        arr.push(data[0].id);
      }
      selectArr = arr;
    } else {
      selectArr = [];
    }
    this.setState({selectArr, isAll});
  };
  _renderItem = ({item, index}) => {
    let {data, selectArr, isAll} = this.state;
    return (
      <TestItem
        item={item}
        onItem={() => this._onItem(item)}
        isSelect={selectArr.indexOf(item.id) != -1}
      />
    );
  };
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: '#f8f8f8',
    flex: 1,
  },
});
