import React from 'react';
import {Image, StyleSheet, Text, TouchableOpacity, View} from "react-native";

function WideGroupPreview({name, description, members, image, onPress}) {
	return (
		<TouchableOpacity
			style={styles.container}
			activeOpacity={0.6}
			onPress={onPress}
		>
			<Image
				style={styles.image}
				source={{uri: image}}
			/>
			
			<View style={styles.information}>
				<Text numberOfLines={1} style={styles.name}>{name}</Text>
				<Text numberOfLines={2} style={styles.description}>{description}</Text>
				<Text style={styles.members}>{members} members</Text>
			</View>
		</TouchableOpacity>
	);
}

const styles = StyleSheet.create({
	container: {
		display: "flex",
		flexDirection: "row",
		borderRadius: 20,
		backgroundColor: "rgb(243, 243, 243)",
		borderWidth: 1,
		borderColor: "rgb(240, 240, 240)",
		overflow: "hidden",
	},
	image: {
		width: 100,
		height: 100,
	},
	information: {
		display: "flex",
		padding: 10,
		flexShrink: 1,
	},
	name: {
		fontSize: 17,
		fontWeight: "bold",
		marginBottom: 2,
	},
	description: {
	},
	members: {
		fontSize: 12,
		fontWeight: "bold",
		opacity: 0.6,
		marginTop: "auto",
	}
})

export default WideGroupPreview;
