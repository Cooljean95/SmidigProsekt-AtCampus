import {Modal, StyleSheet, Text, TouchableOpacity, View} from "react-native";
import {StatusBar} from "expo-status-bar";
import {Picker} from "@react-native-picker/picker";
import {useEffect, useState} from "react";
import LoginButton from "../../components/LoginButton";
import {AntDesign} from "@expo/vector-icons";
import CreateAccountTitle from "../../components/CreateAccountTitle";
import {getAllSchools} from "../../services/SchoolService";

export default function MakeUserSchool({navigation}) {
	
	const SchoolInfo = [
		{
			SchoolName: "Høyskolen kristiania",
			program: [
				"Frontend og mobilutvikling",
				"Programmering",
				"E-bussnins",
				"interaktivt-Dessign",
				"Marketsføring"
			]
		},
		{
			SchoolName: "OsloMet",
			program: [
				"Jeg vet ikke hva de har",
				"men det er sikkert",
				"noe",
				"Bra",
			]
		},
		{
			SchoolName: "UIO",
			program: [
				"Help",
				"Me",
			]
		}
	]
	
	const [isModalSchoolVisible, setIsModalSchoolVisible] = useState(false)
	const [isModalProgramVisible, setIsModalProgramVisible] = useState(false)
	const [school, setSchool] = useState("Høyskolen kristiania")
	const [valueProgram, setValueProgram] = useState("frontend og mobilutvikling")
	const [testSchool, setTestSchool] = useState([])

	useEffect(() => {
		getAllSchools().then((res) => {
			console.log(res)
			setTestSchool(res.data)
		}).catch((err) => {
			console.log(err.toString())
		})
	},[])

	/*
	const {
		setSchool,
		setProgram,
		school,
		program
	} = useContext(CreateUserContext)

	 */
	
	function getProgramFromSchool() {
		const schoolInfo = SchoolInfo.find((element) => element.SchoolName === school)
		return schoolInfo.program
	}
	
	function getProgram(itemValue) {
		setSchool(itemValue)
		const firstProgram = SchoolInfo.find((element) => element.SchoolName === itemValue)
		setValueProgram(firstProgram.program[0])
	}
	
	function onPress() {
		navigation.navigate("makeUserPassword")
	}
	
	return (
		<View style={styles.container}>
			<StatusBar style="auto"/>
			<CreateAccountTitle/>
			
			<View style={styles.viewContainer}>
				<Text style={styles.textSchool}>School</Text>
				<TouchableOpacity style={styles.modalSchool} onPress={() => setIsModalSchoolVisible(true)}>
					<View
						style={styles.schoolInfoWrap}>
						<Text>{school}</Text>
						<AntDesign name="caretdown" size={15} color="black"/>
					</View>
				</TouchableOpacity>
			</View>
			
			<View style={styles.viewContainer}>
				<Text style={styles.textProgram}>Program</Text>
				<TouchableOpacity style={styles.modalProgram} onPress={() => setIsModalProgramVisible(true)}>
					<View
						style={styles.schoolInfoWrap}>
						<Text>{valueProgram}</Text>
						<AntDesign name="caretdown" size={15} color="black"/>
					</View>
				</TouchableOpacity>
			</View>
			
			<Modal
				transparent={false}
				animationType="fade"
				visible={isModalSchoolVisible}
				nRequestClose={() => setIsModalSchoolVisible(false)}
			>
				<View style={styles.backgroundModal}>
					<View style={styles.modalViewSchool}>
						<Text style={styles.textSchool}>School</Text>
						<View style={styles.styleSchool}>
							<Picker
								selectedValue={school}
								onValueChange={(itemValue, itemIndex) => {
									getProgram(itemValue)
								}}
							>
								{SchoolInfo.map((value, i) => (
									<Picker.Item key={i} label={value.SchoolName} value={value.SchoolName}/>
								))}
							</Picker>
						</View>
						<TouchableOpacity style={styles.button} onPress={() => setIsModalSchoolVisible(false)}>
							<Text style={styles.done}>Done</Text>
						</TouchableOpacity>
					</View>
				</View>
			</Modal>
			
			<Modal
				transparent={false}
				animationType="fade"
				visible={isModalProgramVisible}
				nRequestClose={() => setIsModalProgramVisible(false)}
			>
				<View style={styles.backgroundModal}>
					<View style={styles.modalViewSchool}>
						<Text style={styles.textProgram}>Program</Text>
						<View style={styles.styleProgram}>
							<Picker
								selectedValue={valueProgram}
								onValueChange={(itemValue, itemIndex) => {
									setValueProgram(itemValue)
								}}
							>
								{getProgramFromSchool().map((value, i) => (
									<Picker.Item key={i} label={value} value={value}/>
								))}
							</Picker>
						</View>
						<TouchableOpacity style={styles.button} onPress={() => setIsModalProgramVisible(false)}>
							<Text style={styles.done}>Done</Text>
						</TouchableOpacity>
					</View>
				</View>
			</Modal>
			<View style={{marginBottom: 90}}>
				<LoginButton navigation={navigation} title={"Next"} onPress={onPress}/>
			</View>
		</View>
	);
}

const styles = StyleSheet.create({
	container: {
		flex: 1,
		backgroundColor: "#fff",
		padding: 20,
	},
	viewContainer: {
		display: "flex",
		borderRadius: 20,
		marginBottom: 20,
	},
	modalSchool: {
		borderWidth: 1,
		borderRadius: 10,
		borderColor: "#d3d3d3",
		paddingVertical: 5,
		width: "100%",
		paddingHorizontal: 10,
	},
	modalProgram: {
		borderWidth: 1,
		borderRadius: 10,
		borderColor: "#d3d3d3",
		paddingVertical: 5,
		width: "100%",
		paddingHorizontal: 10,
	},
	textSchool: {
		fontWeight: "bold",
		fontSize: 18,
		borderColor: "#d3d3d3",
		width: "100%",
		marginBottom: 10,
	},
	textProgram: {
		fontWeight: "bold",
		fontSize: 18,
		borderColor: "#d3d3d3",
		width: "100%",
		marginBottom: 10,
	},
	schoolInfoWrap: {
		paddingVertical: 15,
		paddingHorizontal: 10,
		flexDirection: "row",
		justifyContent: "space-between",
		alignItems: "center"
	},
	backgroundModal: {
		backgroundColor: "#5b5b5b",
		height: "100%"
	},
	button: {
		alignSelf: "center",
		alignItems: "center",
		backgroundColor: "#7C7FCA",
		width: 300,
		borderRadius: 20,
		padding: 10,
		marginTop: 20
	},
	done: {
		color: "#ffffff",
		fontWeight: "bold",
		fontSize: 15,
	},
	styleSchool: {
		borderWidth: 1,
		width: "110%",
		borderColor: "#d3d3d3",
		borderRadius: 10,
	},
	styleProgram: {
		borderWidth: 1,
		width: "110%",
		borderColor: "#d3d3d3",
		borderRadius: 10,
		
	},
	modalViewSchool: {
		marginTop: 130,
		backgroundColor: "white",
		borderRadius: 20,
		padding: 40,
		alignItems: "center",
		shadowColor: "#000",
		shadowOffset: {
			width: 0,
			height: 10
		},
		shadowOpacity: 0.25,
		shadowRadius: 4,
		elevation: 5
	}
});
