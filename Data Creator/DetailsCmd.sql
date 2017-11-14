USE [BigRestaurent]
GO

/****** Object:  Table [dbo].[DetailsCmd]    Script Date: 14/11/2017 06:31:56 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[DetailsCmd](
	[IdCmd] [int] NOT NULL,
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[IdPlat] [int] NULL,
	[Total] [int] NOT NULL,
	[TotalPrice] [decimal](18, 0) NOT NULL,
	[Remarque] [nvarchar](max) NULL,
 CONSTRAINT [PK_DetailsCmd] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

ALTER TABLE [dbo].[DetailsCmd] ADD  CONSTRAINT [DF_DetailsCmd_Total]  DEFAULT ((0)) FOR [Total]
GO

ALTER TABLE [dbo].[DetailsCmd] ADD  CONSTRAINT [DF_DetailsCmd_TotalPrice]  DEFAULT ((0)) FOR [TotalPrice]
GO

ALTER TABLE [dbo].[DetailsCmd]  WITH CHECK ADD  CONSTRAINT [FK_DetailsCmd_Cmd] FOREIGN KEY([IdCmd])
REFERENCES [dbo].[Cmd] ([Id])
GO

ALTER TABLE [dbo].[DetailsCmd] CHECK CONSTRAINT [FK_DetailsCmd_Cmd]
GO

ALTER TABLE [dbo].[DetailsCmd]  WITH CHECK ADD  CONSTRAINT [FK_DetailsCmd_Plats] FOREIGN KEY([IdPlat])
REFERENCES [dbo].[Plats] ([Id])
GO

ALTER TABLE [dbo].[DetailsCmd] CHECK CONSTRAINT [FK_DetailsCmd_Plats]
GO


