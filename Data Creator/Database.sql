USE [master]
GO

/****** Object:  Database [BigRestaurent]    Script Date: 14/11/2017 06:29:04 ******/
CREATE DATABASE [BigRestaurent]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'BigRestaurent', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\BigRestaurent.mdf' , SIZE = 5120KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'BigRestaurent_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\BigRestaurent_log.ldf' , SIZE = 2048KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO

ALTER DATABASE [BigRestaurent] SET COMPATIBILITY_LEVEL = 120
GO

IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [BigRestaurent].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO

ALTER DATABASE [BigRestaurent] SET ANSI_NULL_DEFAULT OFF 
GO

ALTER DATABASE [BigRestaurent] SET ANSI_NULLS OFF 
GO

ALTER DATABASE [BigRestaurent] SET ANSI_PADDING OFF 
GO

ALTER DATABASE [BigRestaurent] SET ANSI_WARNINGS OFF 
GO

ALTER DATABASE [BigRestaurent] SET ARITHABORT OFF 
GO

ALTER DATABASE [BigRestaurent] SET AUTO_CLOSE OFF 
GO

ALTER DATABASE [BigRestaurent] SET AUTO_SHRINK OFF 
GO

ALTER DATABASE [BigRestaurent] SET AUTO_UPDATE_STATISTICS ON 
GO

ALTER DATABASE [BigRestaurent] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO

ALTER DATABASE [BigRestaurent] SET CURSOR_DEFAULT  GLOBAL 
GO

ALTER DATABASE [BigRestaurent] SET CONCAT_NULL_YIELDS_NULL OFF 
GO

ALTER DATABASE [BigRestaurent] SET NUMERIC_ROUNDABORT OFF 
GO

ALTER DATABASE [BigRestaurent] SET QUOTED_IDENTIFIER OFF 
GO

ALTER DATABASE [BigRestaurent] SET RECURSIVE_TRIGGERS OFF 
GO

ALTER DATABASE [BigRestaurent] SET  DISABLE_BROKER 
GO

ALTER DATABASE [BigRestaurent] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO

ALTER DATABASE [BigRestaurent] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO

ALTER DATABASE [BigRestaurent] SET TRUSTWORTHY OFF 
GO

ALTER DATABASE [BigRestaurent] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO

ALTER DATABASE [BigRestaurent] SET PARAMETERIZATION SIMPLE 
GO

ALTER DATABASE [BigRestaurent] SET READ_COMMITTED_SNAPSHOT OFF 
GO

ALTER DATABASE [BigRestaurent] SET HONOR_BROKER_PRIORITY OFF 
GO

ALTER DATABASE [BigRestaurent] SET RECOVERY SIMPLE 
GO

ALTER DATABASE [BigRestaurent] SET  MULTI_USER 
GO

ALTER DATABASE [BigRestaurent] SET PAGE_VERIFY CHECKSUM  
GO

ALTER DATABASE [BigRestaurent] SET DB_CHAINING OFF 
GO

ALTER DATABASE [BigRestaurent] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO

ALTER DATABASE [BigRestaurent] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO

ALTER DATABASE [BigRestaurent] SET DELAYED_DURABILITY = DISABLED 
GO

ALTER DATABASE [BigRestaurent] SET  READ_WRITE 
GO


