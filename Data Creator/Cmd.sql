USE [BigRestaurent]
GO

/****** Object:  Table [dbo].[Cmd]    Script Date: 14/11/2017 06:31:27 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Cmd](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[IdUser] [int] NULL,
	[IdTable] [int] NULL,
	[TotalHT] [decimal](18, 0) NOT NULL CONSTRAINT [DF_Cmd_TotalHT]  DEFAULT ((0)),
	[Remarque] [nvarchar](max) NULL,
	[Effectuer] [bit] NOT NULL CONSTRAINT [DF_Cmd_Effectuer]  DEFAULT ((0)),
 CONSTRAINT [PK_Cmd] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

ALTER TABLE [dbo].[Cmd]  WITH CHECK ADD  CONSTRAINT [FK_Cmd_ResTables] FOREIGN KEY([IdTable])
REFERENCES [dbo].[ResTables] ([Id])
GO

ALTER TABLE [dbo].[Cmd] CHECK CONSTRAINT [FK_Cmd_ResTables]
GO

ALTER TABLE [dbo].[Cmd]  WITH CHECK ADD  CONSTRAINT [FK_Cmd_Users] FOREIGN KEY([IdUser])
REFERENCES [dbo].[Users] ([Id])
GO

ALTER TABLE [dbo].[Cmd] CHECK CONSTRAINT [FK_Cmd_Users]
GO


