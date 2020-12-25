import React from 'react'
import {configure, shallow} from 'enzyme'
import Adaptor from 'enzyme-adapter-react-16'

import {CreateWorkout} from './CreateWorkout'
import Grid from "@material-ui/core/Grid";
import List from "@material-ui/core/List";
import {ListItem, Button } from "@material-ui/core";
import TextField from "@material-ui/core/TextField";

configure({adapter: new Adaptor()})

// const props = {
//     loginPT: jest.fn(),
//     logoutPT: jest.fn()
// }

describe('<CreateWorkout/>' , ()=> {
    let wrapper;
   
    beforeEach(() => {
        wrapper = shallow(<CreateWorkout pt={()=>{}} exercises={[]} selectedVideos={[]} />)
      });

    it('should render three Grid components (Containers and items)', ()=> {
        
        wrapper.setProps({pt: 1,exercises: [1], selectedVideos: [1]})
        expect(wrapper.find(Grid)).toHaveLength(3)
    })

    it('should render one List component ', ()=> {
        
        wrapper.setProps({pt: 1,exercises: [1], selectedVideos: [1]})
        expect(wrapper.find(List)).toHaveLength(1)
    })

    it('should render one ListItem component ', ()=> {
        
        wrapper.setProps({pt: 1,exercises: [1], selectedVideos: [1]})
        expect(wrapper.find(ListItem)).toHaveLength(1)
    })

    it('should render two Textfield component when there is one video selected (title and description) ', ()=> {
        
        wrapper.setProps({pt: 1,exercises: [1], selectedVideos: [1]})
        expect(wrapper.find(TextField)).toHaveLength(2)
    })

    it('should render one submit Button component ', ()=> {
        
        wrapper.setProps({pt: 1,exercises: [1], selectedVideos: [1]})
        expect(wrapper.find(Button)).toHaveLength(1)
    })
    
})