USE [BigRestaurent]
GO

/****** Object:  Table [dbo].[Plats]    Script Date: 14/11/2017 06:32:21 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Plats](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[IdClass] [int] NOT NULL,
	[Name] [nvarchar](max) NOT NULL,
	[Price] [decimal](18, 0) NOT NULL,
	[Remarque] [nvarchar](max) NULL,
	[Image] [image] NULL,
 CONSTRAINT [PK_Plats] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

ALTER TABLE [dbo].[Plats]  WITH CHECK ADD  CONSTRAINT [FK_Plats_Classes] FOREIGN KEY([IdClass])
REFERENCES [dbo].[Classes] ([Id])
GO

ALTER TABLE [dbo].[Plats] CHECK CONSTRAINT [FK_Plats_Classes]
GO


