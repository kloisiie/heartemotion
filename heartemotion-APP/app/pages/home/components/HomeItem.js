import React, {PureComponent} from 'react';
import {
  StyleSheet,
  Text,
  View,
  Image,
  TouchableWithoutFeedback,
} from 'react-native';
import imageRes from '../../../res/imgeRes';
import statusHandle from '../../../service/statusHandle';
export default class HomeItem extends PureComponent {
  render() {
    let isOpen = this.props.isOpen;
    let {deviceName, wearer, labelStatus, means} = this.props.item;
    let userName = wearer;
    let desc = '高兴';
    let handleStr = `应对手段：${means}`;
    let managerStatus = this.props.managerStatus;
    let isSelect = this.props.isSelect;
    return (
      <TouchableWithoutFeedback onPress={this.props.onItem}>
        <View style={styles.container}>
          <TouchableWithoutFeedback
            onPress={() => {
              if (managerStatus) {
                this.props.onItem();
              } else {
                this.props.onOpen();
              }
            }}>
            <View
              style={{
                backgroundColor: isOpen ? '#5A60E9' : '#EEEEEE',
                width: 56,
                borderTopRightRadius: 8,
                borderBottomRightRadius: 8,
                alignItems: 'center',
                justifyContent: 'center',
              }}>
              {managerStatus ? (
                <View>
                  <Image
                    source={
                      isSelect
                        ? isOpen
                          ? imageRes.icon_select_22_pre_white
                          : imageRes.icon_select_22_pre
                        : imageRes.icon_select_22_nor
                    }
                  />
                </View>
              ) : (
                <>
                  <Image
                    source={
                      isOpen
                        ? imageRes.icon_system1_24_white
                        : imageRes.icon_system1_24_perple
                    }
                  />
                  <View style={{marginTop: 33}}>
                    <Image
                      source={
                        isOpen
                          ? imageRes.icon_link_pre_green
                          : imageRes.icon_link1_nor_gary
                      }
                    />
                  </View>
                </>
              )}
            </View>
          </TouchableWithoutFeedback>
          <View
            style={{flex: 1, marginHorizontal: 16, justifyContent: 'center'}}>
            <View
              style={{flexDirection: 'row', justifyContent: 'space-between'}}>
              <View>
                <Text style={{fontSize: 16, color: '#333', fontWeight: 'bold'}}>
                  {deviceName}
                </Text>
                <Text style={{fontSize: 13, color: '#666', marginTop: 4}}>
                  {userName}
                </Text>
              </View>
              <View
                style={{
                  flexDirection: 'row',
                  alignItems: 'center',
                  alignSelf: 'flex-start',
                }}>
                <Image source={statusHandle.getImage(labelStatus)} />
                <Text
                  style={{
                    fontSize: 16,
                    color: statusHandle.getStatusColor(labelStatus),
                    fontWeight: 'bold',
                    marginLeft: 4,
                  }}>
                  {statusHandle.getStatusStr(labelStatus)}
                </Text>
              </View>
            </View>
            <View
              style={{
                flexDirection: 'row',
                alignItems: 'center',
                marginTop: 16,
              }}>
              <Text
                style={{
                  fontSize: 13,
                  color: '#999',
                  flex: 1,
                }}
                numberOfLines={1}>
                {handleStr}
              </Text>
              {!managerStatus ? null : (
                <TouchableWithoutFeedback onPress={this.props.onEdit}>
                  <View
                    style={{
                      flexDirection: 'row',
                      justifyContent: 'center',
                      alignItems: 'center',
                      marginLeft: 25,
                      width: 58,
                      height: 26,
                      backgroundColor: '#F1F2FF',
                      borderRadius: 13,
                    }}>
                    <Image source={imageRes.icon_tickling_20} />
                    <Text
                      style={{
                        fontSize: 13,
                        color: '#5A60E9',
                      }}>
                      编辑
                    </Text>
                  </View>
                </TouchableWithoutFeedback>
              )}
            </View>
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
    height: 110,
    borderRadius: 8,
    overflow: 'hidden',
    marginBottom: 16,
    flexDirection: 'row',
  },
});
