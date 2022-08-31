import React from 'react';
import {StyleSheet, Text, TouchableOpacity, View} from "react-native";
import {AntDesign} from "@expo/vector-icons";

function ViewMore({text, onPress, style}) {
	return (
		<TouchableOpacity
			activeOpacity={0.6}
			onPress={onPress}
			style={[style, styles.container]}
		>
			<Text style={styles.text}>{text}</Text>
			
			<AntDesign
				style={styles.arrow}
				name={"right"}
				size={18}
				color={"black"}
			/>
		</TouchableOpacity>
	);
}

const styles = StyleSheet.create({
	container: {
		display: "flex",
		flexDirection: "row",
		alignItems: "center",
	},
	text: {
		fontSize: 20,
		fontWeight: "bold",
		marginRight: 5,
	},
	arrow: {
	
	}
})

export default ViewMore;
