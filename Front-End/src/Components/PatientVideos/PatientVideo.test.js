import React from 'react';
import {configure, shallow} from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import {PatientVideos} from './PatientVideo';
import List from "@material-ui/core/List";
import { ListItem } from '@material-ui/core';
import Modal from "@material-ui/core/Modal";
configure({adapter: new Adapter()});

describe('<PatientVideos/>',()=> {
    let wrapper;

    beforeEach(() => {
        wrapper = shallow(<PatientVideos patient_id={()=>{}} selectedPatient={[]} selectedVideo={[]} />);
    });

    it('should render one list of patients List',()=> {
        wrapper.setProps({patient_id: 1, selectedPatient: [1]})
        expect(wrapper.find(List)).toHaveLength(1)
    })
    it('should render one listItem of patients selected',()=> {
        wrapper.setProps({patient_id: 1, selectedVideo: [1]})
        expect(wrapper.find(ListItem)).toHaveLength(0)
    })
    it('should render modal when the SelectedVideo is loading the image', ()=> {
        
        wrapper.setProps({selectedVideos: [1],img: 'https://img.youtube.com/vi/'})
        expect(wrapper.find(Modal)).toHaveLength(1)

    })
})
