module.exports = {
    // site config
    lang: 'en-US',
    title: '庄小焱——努力成长为一名优秀的架构师',
    description: '庄小焱的博客',
    base: '/SeniorArchitect/',
    theme: 'reco',
    themeConfig: {
        themeConfig: {
            logo: '/logo.jpg',
            subSidebar: 'auto'
        },
        vssueConfig: {
            platform: 'github',
            owner: '庄小焱',
            repo: 'SeniorArchitect',
            clientId: 'edc4e71b6515dd9054a9',
            clientSecret: 'f4b0d19920d600cb8c49b51b51c7093da4d45a94',
        },
        nav: [
            {text: '资源导航', link: '/resources/', icon: 'icon-java'},
            {text: '个人简历', link: '/cv/', icon: 'icon-tree'},
            {
                text: '个人社区',
                items: [
                    { text: '个人博客', link: 'https://2462612540.github.io/SeniorArchitect/' },
                    { text: '庄小焱-github', link: 'https://github.com/2462612540' },
                    { text: '庄小焱-gitee', link: 'https://gitee.com/xjl2462612540' },
                    { text: '庄小焱-CSDN', link: 'https://blog.csdn.net/weixin_41605937?type=blog'},
                    { text: '庄小焱-雀语', link: 'https://www.yuque.com/zhuangxiaoyan'}
                ]
            }
        ],
        sidebar: [
            {
                title: "首页",
                path: '/',
                collapsable: true // 不折叠
            },

            {
                title: '资源导航',
                collapsable: true, // 不折叠
                children: [
                    {title: "前言", path: "", collapsable: false},
                ]
            },

            {
                title: '数据结构与算法',
                collapsable: true, // 不折叠
                children: [
                    {title: "前言", path: "", collapsable: false},
                ]
            },

            {
                title: '软件后端开发',
                collapsable: true, // 不折叠
                children: [
                    {title: "前言", path: "", collapsable: false},
                ]
            },

            {
                title: '前端技术',
                collapsable: true, // 不折叠
                children: [
                    {title: "前言", path: "", collapsable: false},
                ]
            },

            {
                title: '大数据与云计算',
                collapsable: true, // 不折叠
                children: [
                    {title: "前言", path: "", collapsable: false},

                ]
            },

            {
                title: '常用开发工具',
                collapsable: true, // 不折叠
                children: [
                    {title: "前言", path: "", collapsable: false},
                ]
            },

            {
                title: "系统架构设计",
                collapsable: true, // 不折叠
                children: [
                    {title: "前言", path: "", collapsable: false},
                ]
            },

            {
                title: "系统设计解决方案",
                collapsable: true, // 不折叠
                children: [
                    {title: "前言", path: "", collapsable: false},
                ]
            },

            {
                title: "项目实战",
                collapsable: true, // 不折叠
                children: [
                    {title: "前言", path: "", collapsable: false},
                ]
            },

            {
                title: "项目管理",
                collapsable: true, // 不折叠
                children: [
                    {title: "前言", path: "", collapsable: false},
                ]
            },

            {
                title: "产品研究",
                collapsable: true, // 不折叠
                children: [
                    {title: "前言", path: "", collapsable: false},
                ]
            },

            {
                title: "大厂面试问题",
                collapsable: true, // 不折叠
                children: [
                    {title: "前言", path: "", collapsable: false},
                ]
            },
        ]
    },
}
