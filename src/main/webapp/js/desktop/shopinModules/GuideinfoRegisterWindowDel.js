/**
 * 导购信息注册
 * @memberOf {TypeName} 
 */
Ext.define('ShopinDesktop.GuideinfoRegisterWindowDel', {
	extend : 'Ext.ux.desktop.Module',
	requires : [ 'Ext.tab.Panel' ,'Ext.grid.Panel'],
	id : 'guideinfoRegisterWindowDel',
	title : "导购信息注册",
	tabs : null,
	init : function() {
		var me = this;
		this.tabs = new Ext.TabPanel({ 
			activeTab : 0,
			height : 380,
			bodyStyle : 'padding: 5px;',
//		this.tabs = [  
			  items : [
			{
				xtype : 'form',
				
				closable : false,
				id : 'baseinfoId',
				title : '基本信息',
				items : [ 
							{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [
//			                    	{
//			                    		xtype : 'textfield',
//										name : 'sid',
//										fieldLabel : 'SID',
//										hidden:true
//									},
			                    	{
		                    			xtype: 'textfield',
		                    			id: 'nameId',
										name : 'name',
										width : 300,
										fieldLabel : '姓名'
									}
									]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'textfield',
										name : 'spell',
										width : 300,
										fieldLabel : '姓名拼音'
									}
				                    ]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
										fieldLabel:"性别",
										xtype:"combo",
										width : 300,
										id:"sexId",
										store:new Ext.data.ArrayStore({
											fields: ['sexCode','value'],
											data : [
											['0',"男"],['1',"女"]
											]
										}),
										valueField: 'sexCode',
										displayField:'value',
										hiddenName:'sexCode',
										triggerAction : 'all',
										name:'sex',
										mode:'local'
									}
			                    ]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'textfield',
										name : 'age',
										width : 300,
										fieldLabel : '年龄'
									}
				                    ]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'textfield',
										name : 'stature',
										width : 300,
										fieldLabel : '身高'
									}
				                    ]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'textfield',
										name : 'mobile',
										width : 300,
										fieldLabel : '联系电话'
									}
			                    ]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'textfield',
										name : 'address',
										width : 300,
										fieldLabel : '家庭住址'
									}
			                    ]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'textfield',
										name : 'presentAddress',
										width : 300,
										fieldLabel : '现住址'
									}
			                    ]
			                },
			                  {
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'textfield',
										name : 'email',
										width : 300,
										fieldLabel : '邮箱'
									}
			                    ]
			                }
//			                ,{
//			                	xtype: 'container',
//			                    layout: 'hbox',
//			                    height:5
//			                },{
//			                	xtype: 'container',
//			                    layout: 'hbox',
//			                    items: [
//			                    	{
//										fieldLabel:"是否是导购",
//										xtype:"combo",
//										width : 300,
//										id:"guideBitId",
//										store:new Ext.data.ArrayStore({
//											fields: ['guideBitCode','value'],
//											data : [
//											['0',"不是"],['1',"是"]
//											]
//										}),
//										valueField: 'guideBitCode',
//										displayField:'value',
//										hiddenName:'guideBitCode',
//										triggerAction : 'all',
//										name:'guideBit',
//										mode:'local'
//									}
//			                    ]
//			                }
					]
//					,
//					buttons :[
//				{
//					text : '保存',
//					handler : function() {
//						alert("save");
//						var arr = new Array();  
//						alert(me.tabs);
//						arr = me.tabs;
////			                	alert("arr===>"+arr.items);
//                for (var i = 0; i < arr.length; i++)
//                {
//                    alert(arr.items)
//                }
//			                me.tabs.each(function(item) { 
//			                	alert("item===>"+item);
//			                    item.show();  
//			                    arr.push(item.items.itemAt(0).form.getValues());  
//			                });  
//			                alert(Ext.encode(arr));  
//			                
//						Ext.Ajax.request({
//			//					url : _appctx + '/padGuideinfo/updateGuidelValidStatus',
//								url : _appctx + '',
//								method:'POST',
//								params: { sid: sid},
//								success: function(response){
//									var result = Ext.decode(response.responseText);
//									if(result.success=="true"){
//										Ext.Msg.alert('','');
//										Ext.getCmp('guideManageId').getStore().load();
//									}else{
//										Ext.Msg.alert('错误','！'+result.msg);
//									}
//								},
//								failure: function(){
//									Ext.Msg.alert('','');
//								}
//							})
//					}
//				},{
//					text : '取消',
//					handler : function() {
//						me.cancel();
//					}
//				} 
//			]
			}, {
				xtype : 'form',
				closable : false,
				id :'mobileId',
				title : '证件信息',
				items : [ 
							{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
					                	xtype: 'container',
					                    layout: 'hbox',
					                    height:5
					                },{
					                	xtype: 'container',
					                    layout: 'hbox',
					                    items: [
					                    	{
				                    			xtype: 'textfield',
												name : 'guideCard',
												width : 300,
												fieldLabel : '身份证号',
												allowBlank : false
											}
					                    ]
					                }]
					          },
					           {
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                 },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'textfield',
										name : 'education',
										width : 300,
										fieldLabel : '学历'
									}
			                    ]
			                 },
					          {
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'textfield',
										name : 'educationCartNum',
										width : 300,
										fieldLabel : '学历证编号'
									}
			                    ]
			                },
			                {
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'textfield',
										name : 'kitasNum',
										width : 300,
										fieldLabel : '暂住证编号'
									}
			                    ]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'datefield',
										name : 'kitasEndTime',
										width : 300,
										fieldLabel : '暂住证有效截止时间',
										format:'Y-m-d H:i:s'
									}
			                    ]
			                },
			                {
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'textfield',
										name : 'healthCartNum',
										width : 300,
										fieldLabel : '健康证编号'
									}
			                    ]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'datefield',
										name : 'healthCartEndTime',
										width : 300,
										fieldLabel : '健康证有效截止时间',
										format:'Y-m-d H:i:s'
									}
			                    ]
			                }
					]
//					,buttons :[
//				{
//					text : '保存',
//					handler : function() {
//						alert("save");
//						var arr = new Array();  
//						alert(me.tabs);
//						arr = me.tabs;
////			                	alert("arr===>"+arr.items);
//                for (var i = 0; i < arr.length; i++)
//                {
//                    alert(arr.items)
//                }
//			                me.tabs.each(function(item) { 
//			                	alert("item===>"+item);
//			                    item.show();  
//			                    arr.push(item.items.itemAt(0).form.getValues());  
//			                });  
//			                alert(Ext.encode(arr));  
//			                
//						Ext.Ajax.request({
//			//					url : _appctx + '/padGuideinfo/updateGuidelValidStatus',
//								url : _appctx + '',
//								method:'POST',
//								params: { sid: sid},
//								success: function(response){
//									var result = Ext.decode(response.responseText);
//									if(result.success=="true"){
//										Ext.Msg.alert('','');
//										Ext.getCmp('guideManageId').getStore().load();
//									}else{
//										Ext.Msg.alert('错误','！'+result.msg);
//									}
//								},
//								failure: function(){
//									Ext.Msg.alert('','');
//								}
//							})
//					}
//				},{
//					text : '取消',
//					handler : function() {
//						me.cancel();
//					}
//				} 
//			]
			}
			]
			})
//			]
			;
	},
	createWindow : function() {
		var me = this;
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow(me.id);
		if (!win) {
			win = desktop.createWindow({
				id : "chooseId",
				title : me.title,
				width : 520,
				height : 380,
//				iconCls : me.launcher.iconCls,
				animCollapse : false,
				border : false,
				constrainHeader : true,
				layout : 'fit',
				items : [ {
//					xtype : 'tabpanel',
//					xtype : 'tabpanel',
//					layout : 'border',
//					activeTab : 0,
//					bodyStyle : 'padding: 5px;',
//					closeAction : 'hide',
					items : me.tabs
				} ],
				buttons :[
				{
					text : '保存',
					handler : function() {
						var arr = new Array();  
						var name = Ext.getCmp('nameId');
						alert(name);
						alert(name.getValue());
//		                me.tabs.items.each(function(item) { 
//		                	alert("item===>"+item);
////		                    item.show(); 
////		                	alert("item.items===>"+item.items.items);
//////		                    debugger;
////		                	for(var i=0;i<item.items.items.length;i++){
//////		                		nameExt.form.TextField.getValue() 
////		                		alert("item.items.items[i]===>"+item.items.items[i]);
////		                		var aa = item.items.items[i];
////		                		alert("aa:"+aa);
////		                	alert("item.items=value==>"+item.items.items[i].TextField.getValue());
////		                	}
////		                	alert("item.items===>"+item.items.items.getValues());
//		                    alert(item.items.itemAt(0).form.getValues());
//		                    arr.push(item.items.itemAt(0).form.getValues());  
//		                });  
		                alert(Ext.encode(arr));  
			                
						Ext.Ajax.request({
			//					url : _appctx + '/padGuideinfo/updateGuidelValidStatus',
								url : _appctx + '',
								method:'POST',
								params: { sid: sid},
								success: function(response){
									var result = Ext.decode(response.responseText);
									if(result.success=="true"){
										Ext.Msg.alert('','');
										Ext.getCmp('guideManageId').getStore().load();
									}else{
										Ext.Msg.alert('错误','！'+result.msg);
									}
								},
								failure: function(){
									Ext.Msg.alert('','');
								}
							})
					}
				},{
					text : '取消',
					handler : function() {
						me.cancel();
					}
				} 
			]
			});
		}
		return win;
	}
});
