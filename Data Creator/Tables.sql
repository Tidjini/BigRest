USE [BigRestaurent]
GO

/****** Object:  Table [dbo].[ResTables]    Script Date: 14/11/2017 06:32:43 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[ResTables](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](max) NOT NULL,
	[Numero] [int] NOT NULL,
	[Remarque] [nvarchar](max) NULL,
	[MaxNumber] [int] NOT NULL CONSTRAINT [DF_ResTables_MaxNumber]  DEFAULT ((1)),
	[State] [int] NOT NULL CONSTRAINT [DF_ResTables_State]  DEFAULT ((0)),
 CONSTRAINT [PK_ResTables] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO


