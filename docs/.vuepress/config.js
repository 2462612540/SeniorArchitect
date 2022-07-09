module.exports = {
    // site config
    lang: 'en-US',
    title: '庄小焱的博客',
    description: '庄小焱的博客',
    base: '/SeniorArchitect/',
    markdown: {
        lineNumbers: true,
            externalLinks: {
            target: '_blank', rel: 'noopener noreferrer'
        }
    },
    locales: {
        "/": {
            lang: "zh-CN",
                title: "架构师知识体系",
                description: "包含: Java基础, Java 部分源码, JVM, Spring, Spring Boot, Spring Cloud, 数据库原理, MySQL, ElasticSearch, MongoDB, Docker, k8s, CI&CD, Linux, DevOps, 分布式, 中间件, 开发工具, Git, IDE, 源码阅读，读书笔记, 开源项目..."
        }
    },
    head: [
        // ico
        ["link", {rel: "icon", href: `/favicon.ico`}],
        // meta
        ["meta", {name: "robots", content: "all"}],
        ["meta", {name: "author", content: "庄小焱"}],
        ["meta", {name: "keywords", content: "架构师知识体系, java体系, java知识体系, java框架,java详解,java学习路线,java spring, java面试, 知识体系, java技术体系, java编程, java编程指南,java开发体系, java开发,java教程,java,java数据结构, 算法, 开发基础"}],
        ["meta", {name: "apple-mobile-web-app-capable", content: "yes"}]
    ],
    theme: 'reco',
    themeConfig: {
        themeConfig: {
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
            {text: '数据结构与算法', link: '/algorithm/', icon: 'icon-tree'},
            {text: '软件后端开发', link: '/backend/', icon: 'icon-design'},
            {text: '前端技术', link: '/frontend/', icon: 'icon-ic_datastores'},
            {text: '大数据与云计算', link: '/bigdata/', icon: 'icon-framework1'},
            {text: '常用开发工具', link: '/tools/', icon: 'icon-distributed'},
            {text: '系统架构设计', link: '/architecture_design/', icon: 'icon-interview'},
            {text: '系统设计解决方案', link: '/system_solutions/', icon: 'icon-interview'},
            {text: '计算机视觉', link: '/computer_version/', icon: 'icon-interview'},
            {text: '项目实战', link: '/project/', icon: 'icon-interview'},
            {text: '项目管理', link: '/project_management/', icon: 'icon-interview'},
            {text: '产品研究', link: '/production/', icon: 'icon-interview'},
            {text: '大厂面试问题', link: '/interview/', icon: 'icon-interview'},
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
