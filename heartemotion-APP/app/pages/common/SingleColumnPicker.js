import React, {PureComponent} from 'react';
import {
  StyleSheet,
  Text,
  View,
  Image,
  TouchableWithoutFeedback,
} from 'react-native';
import {Scrollpicker} from 'beeshell';
export default class SingleColumnPicker extends PureComponent {
  constructor(props) {
    super(props);
    let {index} = props.route.params;
    this.state = {currentIndex: index};
  }
  render() {
    let {data} = this.props.route.params;
    return (
      <View style={styles.container}>
        <TouchableWithoutFeedback onPress={() => GBNavgator.goBack(null)}>
          <View style={{flex: 1}} />
        </TouchableWithoutFeedback>
        <View
          style={{
            backgroundColor: 'white',
            borderTopLeftRadius: 20,
            borderTopRightRadius: 20,
            overflow: 'hidden',
          }}>
          <View
            style={{
              flexDirection: 'row',
              height: 45,
              alignItems: 'center',
              justifyContent: 'space-between',
              backgroundColor: 'white',
              paddingHorizontal: 16,
            }}>
            <Text
              style={{fontSize: 15, color: '#999'}}
              onPress={() => GBNavgator.goBack(null)}>
              取消
            </Text>
            <Text
              style={{fontSize: 15, color: '#4077FF'}}
              onPress={this._onSure}>
              确定
            </Text>
          </View>
          <Scrollpicker
            list={[data]}
            value={[this.state.currentIndex]}
            proportion={[2, 1, 1]}
            onChange={(columnIndex, rowIndex) => {
              console.log(columnIndex, rowIndex);
              this.setState({currentIndex: rowIndex});
            }}
          />
        </View>
      </View>
    );
  }
  _onSure = () => {
    let {onSure} = this.props.route.params;
    onSure && onSure(this.state.currentIndex);
    GBNavgator.goBack(null);
  };
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'flex-end',
  },
});
