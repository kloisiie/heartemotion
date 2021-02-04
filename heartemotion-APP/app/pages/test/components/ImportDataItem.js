import React, {PureComponent} from 'react';
import {
  StyleSheet,
  Text,
  View,
  Image,
  TouchableWithoutFeedback,
} from 'react-native';
import imageRes from '../../../res/imgeRes';
export default class ImportDataItem extends PureComponent {
  render() {
    let {fileName, details} = this.props.item;
    let title = fileName;
    let content = details;
    let isSelect = this.props.isSelect;
    return (
      <TouchableWithoutFeedback onPress={this.props.onItem}>
        <View style={styles.container}>
          <Image
            source={
              isSelect
                ? imageRes.icon_select_22_pre
                : imageRes.icon_select_22_nor
            }
            style={{marginLeft: 8, marginRight: 6}}
          />
          <View style={{flex: 1}}>
            <Text style={{fontSize: 15, color: '#20212A', fontWeight: 'bold'}}>
              {title}
            </Text>
            <Text style={{fontSize: 14, color: '#666', lineHeight: 20}}>
              {content}
            </Text>
          </View>
        </View>
      </TouchableWithoutFeedback>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: 'white',
    marginHorizontal: 16,
    marginBottom: 12,
    paddingVertical: 16,
    borderRadius: 8,
    overflow: 'hidden',
    flexDirection: 'row',
  },
});
