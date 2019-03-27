import React from 'react';
import { TextInput, View, Text } from 'react-native';

const Input = ({ label, value, onChangeText, placeholder, secureTextEntry }) => {

    return (
        <View style={styles.containerStyle}>
            <Text style={styles.labelStyle}>
                {label}
            </Text>

            <TextInput 
                placeholder={placeholder}
                autoCorrect={false}
                value={value}
                onChangeText={onChangeText}
                style={styles.inputStyle}
                secureTextEntry={secureTextEntry}
            />

        </View>
    );

};

const styles = {
    inputStyle: {
        paddingRight: 5,
        paddingLeft: 5,
        paddingTop: 0,
        paddingBottom: 0,
        fontSize: 18,
        lineHeight: 23,
        flex: 2,
        color: '#000'
    },
    labelStyle: {
        flex: 1,
        fontSize: 18,
        paddingLeft: 20
    },
    containerStyle: {
        height: 50,
        flex: 1,
        flexDirection: 'row',
        alignItems: 'center'
    }
}

export { Input };