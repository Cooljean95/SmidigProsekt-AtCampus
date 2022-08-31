import React from 'react';
import {StyleSheet, Text, TouchableOpacity, View} from "react-native";

function SimpleButton({text, onPress, style}) {
	return (
		<TouchableOpacity
			style={[style, styles.container]}
			activeOpacity={0.6}
			onPress={onPress}
		>
			<Text style={styles.text}>{text}</Text>
		</TouchableOpacity>
	);
}

const styles = StyleSheet.create({
	container: {
		paddingVertical: 15,
		paddingHorizontal: 40,
		backgroundColor: "#7c7fca",
		borderRadius: 50,
		width: "100%",
	},
	text: {
		fontSize: 16,
		color: "white",
		textAlign: "center",
	},
})

export default SimpleButton;
