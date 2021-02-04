import React, {PureComponent} from 'react';
import {
  StyleSheet,
  Text,
  View,
  Image,
  TouchableWithoutFeedback,
  FlatList,
  ScrollView,
} from 'react-native';
import imageRes from '../../res/imgeRes';
import NetService from '../../service/NetService';
export default class ChooseAlgorithm extends PureComponent {
  state = {data: [], selectItem: null};
  render() {
    return (
      <TouchableWithoutFeedback onPress={() => GBNavgator.goBack(null)}>
        <View style={styles.container}>
          <View style={styles.bg}>
            <View
              style={{
                height: 50,
                flexDirection: 'row',
                alignItems: 'center',
                paddingHorizontal: 16,
                justifyContent: 'space-between',
              }}>
              <Text style={{fontSize: 15, color: '#333', fontWeight: 'bold'}}>
                选择算法文件执行
              </Text>
              <TouchableWithoutFeedback onPress={() => GBNavgator.goBack(null)}>
                <View>
                  <Image source={imageRes.icon_close_22_666} />
                </View>
              </TouchableWithoutFeedback>
            </View>
            <View style={{height: 0.5, backgroundColor: '#eee'}} />
            <FlatList
              data={this.state.data}
              renderItem={this._renderItem}
              extraData={this.state.selectItem}
            />
            <TouchableWithoutFeedback onPress={this._onSure}>
              <View
                style={{
                  marginTop: 24,
                  marginHorizontal: 16,
                  height: 40,
                  borderRadius: 20,
                  backgroundColor: '#5A60E9',
                  justifyContent: 'center',
                  alignItems: 'center',
                  marginBottom: 20,
                }}>
                <Text style={{fontSize: 16, color: '#fff'}}>确认选择</Text>
              </View>
            </TouchableWithoutFeedback>
          </View>
        </View>
      </TouchableWithoutFeedback>
    );
  }

  async componentDidMount() {
    let {res, error} = await NetService.arithmeticlist();
    if (!error) {
      this.setState({data: res});
    }
  }

  _onSure = () => {
    let {selectItem} = this.state;
    if (!selectItem) {
      GBPrompt.showToast('未选择执行算法');
      return;
    }
    let {onSure} = this.props.route.params;
    onSure && onSure(selectItem);
  };
  _onItem = (item) => {
    // let {selectArr} = this.state;
    // let arr = [];
    // if (selectArr.indexOf(item) != -1) {
    //   arr = selectArr.filter((value) => value != item);
    // } else {
    //   arr = [...selectArr, item];
    // }
    this.setState({selectItem: item});
  };
  _renderItem = ({item, index}) => {
    let {selectItem} = this.state;
    let isSelect = selectItem == item;
    let name = item.name;
    return (
      <TouchableWithoutFeedback onPress={() => this._onItem(item)}>
        <View
          style={{
            flexDirection: 'row',
            alignItems: 'center',
            height: 50,
            paddingHorizontal: 12,
            borderBottomWidth: 1,
            borderColor: '#eee',
          }}>
          <Image
            source={
              isSelect
                ? imageRes.icon_select_22_pre
                : imageRes.icon_select_22_nor
            }
          />
          <Text
            style={{marginLeft: 12, fontSize: 15, color: '#333'}}
            numberOfLines={1}>
            {name}
          </Text>
        </View>
      </TouchableWithoutFeedback>
    );
  };
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'flex-end',
  },
  bg: {
    height: 400,
    borderTopLeftRadius: 20,
    borderTopRightRadius: 20,
    overflow: 'hidden',
    backgroundColor: 'white',
  },
});
