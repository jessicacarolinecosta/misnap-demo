import React, { Component } from 'react';
import { View, Text } from 'react-native';
import { Button } from './common';

import ImagePickerModule from './ImagePicker';
import MiSnapModule from './MiSnapModule';

class Home extends Component {

    onSelectImageButtonPress() {
        console.log('Select Image button pressed');
        const imageArr = MiSnapModule.startMiSnapWorkflow();
        console.log(imageArr);
    }

    render() {
        return(
            <View>
                <View style={styles.buttonContainerStyle}>
                    <Button 
                        title = 'Select Image'
                        onPress = { this.onSelectImageButtonPress.bind(this) } 
                    />
                </View>
            </View>
        );
    }

}

const styles = {
    buttonContainerStyle: {
        minHeight: 94
    }
}

export default Home;