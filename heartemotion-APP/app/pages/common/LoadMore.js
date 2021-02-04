import {
  ActivityIndicator,
  Text,
  TouchableWithoutFeedback,
  View,
} from 'react-native';
import React from 'react';
import ListEndView from './ListEndView';

const LoadMore = (props) => {
  let {loadMoreing, haveMore, callback} = props;
  if (loadMoreing) {
    return (
      <View
        style={{height: 43, justifyContent: 'center', alignItems: 'center'}}>
        <ActivityIndicator color={'#FF5242'} />
      </View>
    );
  }
  if (haveMore) {
    return (
      <TouchableWithoutFeedback onPress={callback}>
        <View
          style={{
            height: 43,
            justifyContent: 'center',
            alignItems: 'center',
            backgroundColor: '#F8F8F8',
          }}>
          <Text style={{fontSize: 12, color: '#999999'}}>-点击加载更多-</Text>
        </View>
      </TouchableWithoutFeedback>
    );
  } else {
    return <ListEndView />;
    // if (noMoreText) {
    //   return (
    //     <TouchableWithoutFeedback onPress={callback}>
    //       <View
    //         style={{
    //           height: 30,
    //           justifyContent: 'center',
    //           alignItems: 'center',
    //         }}>
    //         <Text style={props.noMoreStyle}>{noMoreText}</Text>
    //       </View>
    //     </TouchableWithoutFeedback>
    //   );
    // }
  }
};

export default LoadMore;
