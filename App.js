import React from "react";
import {Text} from 'react-native';
import {useState,useEffect} from 'react';

import HintRequestPicker from './.vscode/HintRequest';

function App(){

  const [number,setNumber]= useState('');


  useEffect(() => {
const fetchHintRequest = async() => {
  const hintnumber= await HintRequestPicker.getPhoneNumber();
  setNumber(hintnumber)

};
fetchHintRequest();

},[]);



HintRequestPicker.showToast();
//HintRequestPicker.getPhoneNumber();
  return(

   //<Text>Hello from React</Text>   
   <Text>{number}</Text>
  );
}
export default App;