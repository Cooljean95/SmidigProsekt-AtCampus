import React from 'react';
import {StyleSheet, Text, TextInput, View} from "react-native";

function InputField({
    title,
    style,
    multiline,
    keyboardType,
    secureTextEntry,
    value,
    onChangeText,
    onSubmitEditing,
    returnKeyType,
    returnKeyLabel,
    blurOnSubmit,
    innerRef,
    autoCapitalize,
    autoComplete,
    autoCorrect,
    textContentType,
    placeholder,
}) {
	return (
		<View style={styles.container}>
			<Text style={styles.text}>{title}</Text>
			
			<TextInput
				ref={innerRef}
				multiline={multiline}
				style={[style, styles.input]}
				placeholder={placeholder}
				onChangeText={onChangeText}
				value={value}
				secureTextEntry={secureTextEntry}
				keyboardType={keyboardType}
				onSubmitEditing={onSubmitEditing}
				returnKeyType={returnKeyType}
				returnKeyLabel={returnKeyLabel}
				blurOnSubmit={blurOnSubmit}
				autoCapitalize={autoCapitalize}
				autoComplete={autoComplete}
				autoCorrect={autoCorrect}
				textContentType={textContentType}
				placeholderTextColor={"rgb(120, 120, 120)"}
			/>
		</View>
	);
}

const styles = StyleSheet.create({
	container: {
		display: "flex",
		width: "100%",
	},
	input: {
		backgroundColor: "white",
		borderWidth: 1,
		borderColor: "#f2f4f5",
		paddingVertical: 15,
		paddingHorizontal: 20,
		paddingTop: 15,
		borderRadius: 15,
		fontSize: 16,
	},
	text: {
		fontSize: 18,
		fontWeight: "bold",
		marginBottom: 10,
	}
})

export default InputField;
