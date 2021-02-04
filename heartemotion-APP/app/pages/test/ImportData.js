import React, {PureComponent} from 'react';
import {
  StyleSheet,
  Text,
  View,
  Image,
  FlatList,
  TouchableWithoutFeedback,
} from 'react-native';
import ImportDataItem from './components/ImportDataItem';
import NetService from '../../service/NetService';
export default class ImportData extends PureComponent {
  state = {data: [], selectArr: [], selectItem: null};
  render() {
    return (
      <View style={styles.container}>
        <FlatList
          style={{marginTop: 12, flex: 1}}
          data={this.state.data}
          renderItem={this._renderItem}
          onEndReachedThreshold={0.01}
          onEndReached={() => {
            this.pageIndex++;
            this._loadlist();
          }}
        />
        <View
          style={{
            height: 50,
            backgroundColor: 'white',
            flexDirection: 'row',
            justifyContent: 'flex-end',
            paddingTop: 8,
          }}>
          {/*<TouchableWithoutFeedback onPress={this._onDelete}>*/}
          {/*  <View*/}
          {/*    style={{*/}
          {/*      width: 83,*/}
          {/*      height: 34,*/}
          {/*      borderRadius: 17,*/}
          {/*      borderWidth: 1,*/}
          {/*      borderColor: '#FF4E56',*/}
          {/*      justifyContent: 'center',*/}
          {/*      alignItems: 'center',*/}
          {/*      marginRight: 12,*/}
          {/*    }}>*/}
          {/*    <Text*/}
          {/*      style={{fontSize: 15, color: '#FF4E56', fontWeight: 'bold'}}>*/}
          {/*      删除*/}
          {/*    </Text>*/}
          {/*  </View>*/}
          {/*</TouchableWithoutFeedback>*/}
          <TouchableWithoutFeedback onPress={this._onRun}>
            <View
              style={{
                width: 83,
                height: 34,
                borderRadius: 17,
                backgroundColor: '#5A60E9',
                justifyContent: 'center',
                alignItems: 'center',
                marginRight: 12,
              }}>
              <Text style={{fontSize: 15, color: '#fff', fontWeight: 'bold'}}>
                添加
              </Text>
            </View>
          </TouchableWithoutFeedback>
        </View>
      </View>
    );
  }
  componentDidMount() {
    this.pageIndex = 1;
    this._loadlist();
  }
  _loadlist = async () => {
    let param = {pageIndex: this.pageIndex, pageSize: 12};
    let {res, error} = await NetService.platformDebugList(param);
    let arr = [];
    if (this.pageIndex == 1) {
      arr = res.list;
      this.setState({data: arr});
    } else {
      // if (res.list.length == 0) {
      //   return;
      // }
      // arr = [...this.state.data, ...res.list];
      // this.setState({data: arr});
    }
  };
  _onRun = async () => {
    let {selectItem} = this.state;
    if (!selectItem) {
      GBPrompt.showToast('未选择文件');
      return;
    }
    let {res, error} = await NetService.addPlatformFile(selectItem);
    if (!error) {
      GBNavgator.goBack(null);
    }
  };
  _onItem = (item) => {
    // let {selectArr} = this.state;
    // let arr = [];
    // if (selectArr.indexOf(item.id) != -1) {
    //   arr = selectArr.filter((value) => value != item.id);
    // } else {
    //   arr = [...selectArr, item.id];
    // }
    // this.setState({selectArr: arr});
    let {selectItem} = this.state;
    if (selectItem === item.id) {
      selectItem = null;
    } else {
      selectItem = item.id;
    }
    this.setState({selectItem});
  };
  _renderItem = ({item, index}) => {
    return (
      <ImportDataItem
        item={item}
        isSelect={item.id === this.state.selectItem}
        onItem={() => this._onItem(item)}
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
