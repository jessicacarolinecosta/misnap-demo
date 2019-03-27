import React from 'react';
import { Text, TouchableOpacity } from 'react-native';


const Button = ({title, onPress}) => {
    return (
        <TouchableOpacity onPress={onPress} style={styles.buttonStyle}>
            <Text style={styles.textStyle}>{title}</Text>
        </TouchableOpacity>
    );
}


const styles = {
    textStyle: {
        alignSelf: 'center',
        color: '#fff',
        fontSize: 16,
        fontWeight: '500',
        paddingTop: 16,
        paddingBottom: 16
    },
    buttonStyle: {
        flex: 1,
        alignSelf: 'stretch',
        backgroundColor: '#039BE5',
        borderRadius: 8,
        //borderWidth: 2,
        //borderColor: '#007aff',
        margin: 20,
        maxHeight: 56
    }
}

export { Button };
