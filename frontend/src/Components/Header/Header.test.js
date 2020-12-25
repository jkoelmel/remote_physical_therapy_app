import React from 'react'
import {configure, shallow} from 'enzyme'
import Adaptor from 'enzyme-adapter-react-16'

import {Header} from './Header'
import Menu from "@material-ui/core/Menu";
import { AppBar, Button, Toolbar } from '@material-ui/core';
import IconButton from "@material-ui/core/IconButton";
configure({adapter: new Adaptor()})

// const props = {
//     loginPT: jest.fn(),
//     logoutPT: jest.fn()
// }

describe('<Header/>' , ()=> {
    let wrapper;

    beforeEach(() => {
        wrapper = shallow(<Header pt={()=>{}} logoutPT={()=>{}}/>)
      });

    it('should render one menu button ', ()=> {
        
        wrapper.setProps({pt: 1,logoutPT: 1})
        expect(wrapper.find(Menu)).toHaveLength(2)
    })

    it('should render one App bar', ()=> {
        
        wrapper.setProps({pt: 1,logoutPT: 1})
        expect(wrapper.find(AppBar)).toHaveLength(1)
    })
    
    it('should render two icon buttons', ()=> {
        
        wrapper.setProps({pt: 1,logoutPT: 1})
        expect(wrapper.find(IconButton)).toHaveLength(2)
    })

    it('should render one logo button', ()=> {
        
        wrapper.setProps({pt: 1,logoutPT: 1})
        expect(wrapper.find(Button)).toHaveLength(1)
    })

    it('should render one tool bar', ()=> {
        
        wrapper.setProps({pt: 1,logoutPT: 1})
        expect(wrapper.find(Toolbar)).toHaveLength(1)
    })
})