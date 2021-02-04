import React, {PureComponent} from 'react';
import {StyleSheet, Text, View, Image, FlatList} from 'react-native';
import AlarmRecordItem from './components/AlarmRecordItem';
import NetService from '../../service/NetService';
export default class AlarmRecord extends PureComponent {
  state = {data: []};
  render() {
    return (
      <View style={styles.container}>
        <FlatList
          data={this.state.data}
          renderItem={this._renderItem}
          ItemSeparatorComponent={this._ItemSeparatorComponent}
          onEndReachedThreshold={0.1}
          onEndReached={() => {
            this.pageIndex++;
            this._loadList();
          }}
        />
      </View>
    );
  }
  componentDidMount() {
    this.pageIndex = 1;
    this._loadList();
  }
  _loadList = async () => {
    let {deviceId} = this.props.route.params;
    let param = {deviceId, pageIndex: this.pageIndex, pageSize: 12};
    let {res, error} = await NetService.warnList(param);
    let arr = [];
    if (this.pageIndex == 1) {
      arr = res.list;
    } else {
      arr = [...this.state.data, ...res.list];
    }
    this.setState({data: arr});
  };

  _renderItem = ({item, index}) => {
    return <AlarmRecordItem item={item} />;
  };
  _ItemSeparatorComponent = () => {
    return (
      <View style={{height: 0.5, backgroundColor: '#eee', marginLeft: 50}} />
    );
  };
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: 'white',
    flex: 1,
  },
});
