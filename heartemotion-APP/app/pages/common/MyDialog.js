import React from 'react';
import {
  View,
  StyleSheet,
  Text,
  TouchableWithoutFeedback,
  Dimensions,
} from 'react-native';
export default class MyDialog extends React.PureComponent {
  render() {
    let {
      title,
      content,
      cancelText,
      sureText,
      cancelAction,
      sureAction,
    } = this.props.route.params;
    return (
      <View style={styles.container}>
        <View style={styles.bg}>
          <View
            style={{
              paddingVertical: 20,
              paddingHorizontal: 16,
              alignItems: 'center',
            }}>
            {!title ? null : (
              <Text
                style={{
                  fontSize: 18,
                  color: '#333',
                  fontFamily: 'PingFangSC-Semibold',
                  textAlign: 'center',
                }}>
                {title}
              </Text>
            )}
            {!content ? null : (
              <Text
                style={{
                  fontSize: 16,
                  color: '#333',
                  marginTop: 5,
                  lineHeight: 20,
                  textAlign: 'center',
                }}>
                {content}
              </Text>
            )}
          </View>
          <View style={{height: 0.5, backgroundColor: '#eee'}} />
          {cancelText && sureText ? (
            <View style={{height: 45, flexDirection: 'row'}}>
              <TouchableWithoutFeedback onPress={() => this._onAciton(1)}>
                <View
                  style={{
                    flex: 1,
                    justifyContent: 'center',
                    alignItems: 'center',
                  }}>
                  <Text
                    style={{
                      fontSize: 17,
                      color: '#333',
                    }}>
                    {cancelText}
                  </Text>
                </View>
              </TouchableWithoutFeedback>
              <View style={{width: 0.5, backgroundColor: '#eee'}} />
              <TouchableWithoutFeedback onPress={() => this._onAciton(2)}>
                <View
                  style={{
                    flex: 1,
                    justifyContent: 'center',
                    alignItems: 'center',
                  }}>
                  <Text
                    style={{
                      fontSize: 17,
                      color: '#576B95',
                      fontWeight: 'bold',
                    }}>
                    {sureText}
                  </Text>
                </View>
              </TouchableWithoutFeedback>
            </View>
          ) : (
            <View style={{height: 45, flexDirection: 'row'}}>
              <TouchableWithoutFeedback onPress={() => this._onAciton(2)}>
                <View
                  style={{
                    flex: 1,
                    justifyContent: 'center',
                    alignItems: 'center',
                  }}>
                  <Text
                    style={{
                      fontSize: 18,
                      color: '#4077FF',
                      fontFamily: 'PingFangSC-Semibold',
                    }}>
                    {sureText}
                  </Text>
                </View>
              </TouchableWithoutFeedback>
            </View>
          )}
        </View>
      </View>
    );
  }
  componentDidMount() {
    this.sureFlag = false;
    this.index = -1;
  }

  componentWillUnmount() {
    if (this.sureFlag) {
      let {cancelAction, sureAction} = this.props.route.params;
      if (this.index == 1) {
        cancelAction && cancelAction();
      } else {
        sureAction && sureAction();
      }
    }
  }

  _onAciton = (index) => {
    this.props.navigation.goBack(null);
    this.sureFlag = true;
    this.index = index;
  };
}
const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'rgba(0,0,0,0.5)',
  },
  bg: {
    width: 280,
    backgroundColor: 'white',
    borderRadius: 8,
    overflow: 'hidden',
  },
});
