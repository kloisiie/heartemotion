import React, {PureComponent} from 'react';
import {
  StyleSheet,
  Text,
  View,
  Image,
  ScrollView,
  TextInput,
  TouchableWithoutFeedback,
} from 'react-native';
import {setBaseUrl, setToken} from 'react-native-request';
import SharePreference from 'react-native-share-preference';
import Keys from '../../constant/keys';
import NetService from '../../service/NetService';
import hostService from '../../service/hostService';
export default class HostSetting extends PureComponent {
  state = {host: '', port: ''};
  render() {
    let {host, port} = this.state;
    return (
      <ScrollView style={styles.container}>
        <View
          style={{
            flexDirection: 'row',
            alignItems: 'center',
            paddingHorizontal: 16,
            justifyContent: 'space-between',
            height: 50,
            borderBottomWidth: 0.5,
            borderColor: '#eee',
          }}>
          <Text style={{fontSize: 15, color: '#666'}}>服务器地址</Text>
          <TextInput
            style={{
              fontSize: 15,
              color: '#333',
              padding: 0,
              width: 250,
              height: 50,
            }}
            textAlign={'right'}
            value={host}
            onChangeText={(host) => this.setState({host})}
            placeholder={'输入服务器地址'}
            placeholderTextColor={'#999'}
          />
        </View>
        <View
          style={{
            flexDirection: 'row',
            alignItems: 'center',
            paddingHorizontal: 16,
            justifyContent: 'space-between',
            height: 50,
            borderBottomWidth: 0.5,
            borderColor: '#eee',
          }}>
          <Text style={{fontSize: 15, color: '#666'}}>端口号</Text>
          <TextInput
            style={{
              fontSize: 15,
              color: '#333',
              padding: 0,
              width: 250,
              height: 50,
            }}
            textAlign={'right'}
            value={port}
            onChangeText={(port) => this.setState({port})}
            placeholder={'输入端口号'}
            placeholderTextColor={'#999'}
          />
        </View>
        <TouchableWithoutFeedback onPress={this._onSure}>
          <View
            style={{
              marginTop: 48,
              marginHorizontal: 16,
              height: 40,
              borderRadius: 20,
              backgroundColor: '#5A60E9',
              justifyContent: 'center',
              alignItems: 'center',
              marginBottom: 20,
            }}>
            <Text style={{fontSize: 16, color: '#fff'}}>提交更改</Text>
          </View>
        </TouchableWithoutFeedback>
      </ScrollView>
    );
  }
  async componentDidMount() {
    let str = await SharePreference.getValue(Keys.host_data_key);
    console.log('设置的数据 ', str);
    if (str && str.length > 0) {
      let data = JSON.parse(str);
      this.setState({...data});
    }
  }

  _onSure = async () => {
    let {host, port} = this.state;
    if (host.length == 0) {
      GBPrompt.showToast('未输入服务器地址');
      return;
    }
    if (port.length == 0) {
      GBPrompt.showToast('未输入端口号');
      return;
    }
    GBPrompt.showLoading(true);
    let url = `${host}:${port}/`;
    console.log('url ', url);
    setBaseUrl(url);
    let {res, error} = await NetService.login();
    if (!error && res) {
      setToken(res.token);
      global.GBUser = res;
      SharePreference.saveValue(Keys.user_info_key, JSON.stringify(res));
      await SharePreference.saveValue(
        Keys.host_data_key,
        JSON.stringify(this.state),
      );
      GBPrompt.showToast('配置成功');
    } else {
      //setToken('');
      //global.GBUser = null;
      let host = await hostService.getHost();
      setBaseUrl(host);
      GBPrompt.showToast('配置失败,请检查主机地址或端口号');
    }
    GBPrompt.showLoading(false);
  };
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: 'white',
    flex: 1,
  },
});
